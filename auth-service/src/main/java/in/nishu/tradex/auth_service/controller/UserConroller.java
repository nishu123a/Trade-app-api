package in.nishu.tradex.auth_service.controller;

import in.nishu.tradex.auth_service.dtos.AuthDtos.UserResponse;
import in.nishu.tradex.auth_service.dtos.UserDtos.ChangePasswordRequest;
import in.nishu.tradex.auth_service.service.UserService;
import in.nishu.tradex.common_lib.security.JwtPrincipal;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    UserResponse updateProfile(@AuthenticationPrincipal JwtPrincipal token){
        return userService.me(token);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changePassword(@AuthenticationPrincipal JwtPrincipal token,
                        @Valid @RequestBody ChangePasswordRequest request){
        userService.changePassword(token,request);
    }
}
