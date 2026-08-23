package in.nishu.tradex.auth_service.controller;

import in.nishu.tradex.auth_service.dtos.AuthDtos.UserResponse;
import in.nishu.tradex.auth_service.dtos.UserDtos;
import in.nishu.tradex.auth_service.dtos.UserDtos.ChangePasswordRequest;
import in.nishu.tradex.auth_service.service.UserService;
import in.nishu.tradex.common_lib.security.JwtPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserConroller {
    private final UserService userService;
    @GetMapping("/me")
    UserResponse me(@AuthenticationPrincipal JwtPrincipal token){
        return userService.me(token);
    }

    @PutMapping("/me")
    UserResponse updateProfile(@AuthenticationPrincipal JwtPrincipal token,
                                @RequestBody UserDtos.UpdateProfileRequest request) {
        System.out.println("STEP 1 - Controller reached");
        System.out.println("FULL NAME = [" + request.fullName() + "]");


        return userService.updateProfile(token, request);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changePassword(@AuthenticationPrincipal JwtPrincipal token,
                        @Valid @RequestBody ChangePasswordRequest request){
        userService.changePassword(token,request);
    }
}
