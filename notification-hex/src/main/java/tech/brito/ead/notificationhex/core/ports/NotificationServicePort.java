package tech.brito.ead.notificationhex.core.ports;

import tech.brito.ead.notificationhex.core.domain.NotificationDomain;
import tech.brito.ead.notificationhex.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationServicePort {

    NotificationDomain save(NotificationDomain notificationDomain);

    List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo);

    Optional<NotificationDomain> findByIdAndUserId(UUID notificationId, UUID userId);
}
