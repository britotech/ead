package tech.brito.ead.notification.domain.services;

import org.springframework.stereotype.Service;
import tech.brito.ead.notification.domain.repositories.NotificationRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
