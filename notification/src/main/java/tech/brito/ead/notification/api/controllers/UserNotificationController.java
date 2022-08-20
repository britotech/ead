package tech.brito.ead.notification.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.notification.domain.models.Notification;
import tech.brito.ead.notification.domain.services.NotificationService;
import tech.brito.ead.notification.dtos.NotificationDTO;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}/notifications")
    public Page<Notification> getAllNotificationsByUser(@PathVariable UUID userId, @PageableDefault(sort = "id") Pageable pageable) {
        return notificationService.findAllNotificationsByUser(userId, pageable);
    }

    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public Notification updateNotification(@PathVariable UUID userId,
                                           @PathVariable UUID notificationId,
                                           @RequestBody @Valid NotificationDTO notificationDTO) {

        var notification = notificationService.findByIdAndUserId(notificationId, userId);
        notification.setStatus(notificationDTO.getStatus());
        return notificationService.save(notification);
    }
}
