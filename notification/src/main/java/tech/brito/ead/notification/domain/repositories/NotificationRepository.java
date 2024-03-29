package tech.brito.ead.notification.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.notification.domain.models.Notification;
import tech.brito.ead.notification.enums.NotificationStatus;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findAllByUserIdAndStatus(UUID userId, NotificationStatus status, Pageable pageable);

    Optional<Notification> findByIdAndUserId(UUID id, UUID userId);
}
