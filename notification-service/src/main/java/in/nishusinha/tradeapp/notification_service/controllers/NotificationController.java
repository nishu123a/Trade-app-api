package in.nishusinha.tradeapp.notification_service.controllers;

import in.nishusinha.tradeapp.common_lib.security.JwtPrincipal;
import in.nishusinha.tradeapp.notification_service.dtos.NotificationDtos;
import in.nishusinha.tradeapp.notification_service.services.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final UserNotificationService userNotificationService;

    @GetMapping
    public List<NotificationDtos.NotificationResponse> notifications(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestParam(defaultValue = "100") int limit) {
        return userNotificationService.notifications(principal, limit);
    }

    @GetMapping("/unread-count")
    public NotificationDtos.UnreadCountResponse unreadCount(@AuthenticationPrincipal JwtPrincipal principal) {
        return userNotificationService.unreadCount(principal);
    }

    @PatchMapping("/{id}/read")
    public NotificationDtos.NotificationResponse markAsRead(@AuthenticationPrincipal JwtPrincipal principal,
            @PathVariable Long id) {
        return userNotificationService.markAsRead(principal, id);
    }

    @PostMapping("/read-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAllAsRead(@AuthenticationPrincipal JwtPrincipal principal) {
        userNotificationService.markAllAsRead(principal);
    }
}

