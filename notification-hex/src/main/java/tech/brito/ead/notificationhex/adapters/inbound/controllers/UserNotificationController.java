package tech.brito.ead.notificationhex.adapters.inbound.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.notificationhex.adapters.dtos.NotificationDTO;
import tech.brito.ead.notificationhex.core.domain.NotificationDomain;
import tech.brito.ead.notificationhex.core.domain.PageInfo;
import tech.brito.ead.notificationhex.core.ports.NotificationServicePort;
import tech.brito.ead.notificationhex.exceptions.NotificationNotFoundException;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationServicePort notificationServicePort;

    public UserNotificationController(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public Page<NotificationDomain> getAllNotificationsByUser(@PathVariable UUID userId, @PageableDefault(sort = "id") Pageable pageable) {
        var pageInfo = new PageInfo();
        BeanUtils.copyProperties(pageable, pageInfo);

        var notifications = notificationServicePort.findAllNotificationsByUser(userId, pageInfo);
        return new PageImpl<>(notifications, pageable, notifications.size());
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public NotificationDomain updateNotification(@PathVariable UUID userId,
                                                 @PathVariable UUID notificationId,
                                                 @RequestBody @Valid NotificationDTO notificationDTO) {

        var notificationOptional = notificationServicePort.findByIdAndUserId(notificationId, userId);
        if (notificationOptional.isEmpty()) {
            throw new NotificationNotFoundException();
        }

        notificationOptional.get().setStatus(notificationDTO.getStatus());
        return notificationServicePort.save(notificationOptional.get());
    }
}
