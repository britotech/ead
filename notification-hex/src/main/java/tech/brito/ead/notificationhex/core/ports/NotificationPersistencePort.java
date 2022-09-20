package tech.brito.ead.notificationhex.core.ports;

import tech.brito.ead.notificationhex.core.domain.NotificationDomain;
import tech.brito.ead.notificationhex.core.domain.PageInfo;
import tech.brito.ead.notificationhex.core.domain.enums.NotificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {

    NotificationDomain save(NotificationDomain notificationDomain);

    List<NotificationDomain> findAllNotificationsByUserAndStatus(UUID userId, NotificationStatus status, PageInfo pageInfo);

    Optional<NotificationDomain> findByIdAndUserId(UUID notificationId, UUID userId);
}
