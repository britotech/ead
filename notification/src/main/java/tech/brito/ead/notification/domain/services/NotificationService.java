package tech.brito.ead.notification.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.brito.ead.notification.domain.exceptions.NotificationNotFoundException;
import tech.brito.ead.notification.domain.models.Notification;
import tech.brito.ead.notification.domain.repositories.NotificationRepository;
import tech.brito.ead.notification.enums.NotificationStatus;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Page<Notification> findAllNotificationsByUser(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndStatus(userId, NotificationStatus.CREATED, pageable);
    }

    public Notification findByIdAndUserId(UUID notificationId, UUID userId) {
        return notificationRepository.findByIdAndUserId(notificationId, userId).orElseThrow(NotificationNotFoundException::new);
    }
}
