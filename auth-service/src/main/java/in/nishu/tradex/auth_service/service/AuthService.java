package in.nishu.tradex.auth_service.service;

import in.nishu.tradex.auth_service.dtos.AuthDtos.UserResponse;
import in.nishu.tradex.auth_service.dtos.AuthDtos.AuthResponse;
import in.nishu.tradex.auth_service.dtos.AuthDtos.LoginRequest;
import in.nishu.tradex.auth_service.dtos.AuthDtos.SignupRequest;

import in.nishu.tradex.auth_service.entity.RevokedToken;
import in.nishu.tradex.auth_service.entity.User;
import in.nishu.tradex.auth_service.repositories.RevokedTokenRepository;
import in.nishu.tradex.auth_service.repositories.UserRepository;
import in.nishu.tradex.common_lib.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        String email = request.email().trim().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        User userObj = User.builder()
                .email(email)
                .fullName(request.fullName().trim())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        if (userObj == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Register user");
        }
        User user = userRepository.save(userObj);
        return tokensFor(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid Email or Password"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid Email or Password");
        }
        return tokensFor(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse refresh(String refreshToken) {
        if (refreshToken == null || revokedTokenRepository.existsById(sha256(refreshToken)) || !jwtTokenService.isRefreshToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        var parsedToken = jwtTokenService.parse(refreshToken);

        User user = userRepository.findById(parsedToken.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));
        return tokensFor(user);
    }

    @Transactional
    public void logout(String refreshToken) {
        revokedTokenRepository.save(new RevokedToken(sha256(refreshToken)));
    }

    private AuthResponse tokensFor(User user){
        Set<String> roles = Set.copyOf(user.getRoles().stream().sorted().map(Enum::toString).toList());

        return new AuthResponse(
                jwtTokenService.createAccessToken(user.getId(), user.getEmail(), roles),
                jwtTokenService.createRefreshToken(user.getId(), user.getEmail(), roles),
                toResponse(user));
    }
    UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getFullName(), user.getRoles(), user.getCreatedAt());
    }

    private String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-2256").digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable ", exception);
        }
    }
}
