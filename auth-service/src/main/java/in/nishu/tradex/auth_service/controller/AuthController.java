package in.nishu.tradex.auth_service.controller;

import in.nishu.tradex.auth_service.dtos.AuthDtos.SignupRequest;
import in.nishu.tradex.auth_service.service.AuthService;
import jakarta.validation.Valid;
import in.nishu.tradex.auth_service.dtos.AuthDtos.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import in.nishu.tradex.auth_service.dtos.AuthDtos.LogoutRequest;
import in.nishu.tradex.auth_service.dtos.AuthDtos.RefreshRequest;
import in.nishu.tradex.auth_service.dtos.AuthDtos.LoginRequest;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController{

    private final AuthService authService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signup(@Valid @RequestBody SignupRequest request){
        return authService.signup(request);
    }
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }
    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest request) {
        return authService.refresh(request.refreshToken());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody LogoutRequest request){
        authService.logout(request.refreshToken());
    }
}
