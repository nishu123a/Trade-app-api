package in.nishu.tradex.auth_service.service;

import in.nishu.tradex.auth_service.dtos.AuthDtos.UserResponse;
import in.nishu.tradex.auth_service.dtos.UserDtos.ChangePasswordRequest;
import in.nishu.tradex.auth_service.entity.User;
import in.nishu.tradex.auth_service.repositories.UserRepository;
import in.nishu.tradex.common_lib.security.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import in.nishu.tradex.auth_service.dtos.UserDtos.UpdateProfileRequest;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    @Transactional
    public UserResponse updateProfile(JwtPrincipal parsedToken, UpdateProfileRequest updateProfileRequest) {
        User user = findUser(parsedToken);
        user.setFullName(updateProfileRequest.fullName().trim());
        return authService.toResponse(user);
    }

    @Transactional
    public void changePassword(JwtPrincipal parsedToken,ChangePasswordRequest request) {
        User user = findUser(parsedToken);
    if(!passwordEncoder.matches(request.currentPassword(),user.getPasswordHash())){
        throw new BadCredentialsException("Current Password is Incorrect");
    }
    user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
  }
      private User findUser(JwtPrincipal parsedToken){
            return userRepository.findById(parsedToken.userId())
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));
      }

      @Transactional(readOnly = true)
        public UserResponse me(JwtPrincipal parsedToken) {
            return authService.toResponse(findUser(parsedToken));
        }
}
