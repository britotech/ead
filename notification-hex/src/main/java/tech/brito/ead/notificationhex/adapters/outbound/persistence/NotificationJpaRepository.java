package tech.brito.ead.notificationhex.adapters.outbound.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import tech.brito.ead.notificationhex.core.domain.enums.NotificationStatus;

import java.util.Optional;
import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findAllByUserIdAndStatus(UUID userId, NotificationStatus status, Pageable pageable);

    Optional<NotificationEntity> findByIdAndUserId(UUID id, UUID userId);
}
