package tech.brito.ead.notification.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.notification.domain.models.Notification;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
