package tech.brito.ead.notificationhex.core.services;

import tech.brito.ead.notificationhex.core.domain.NotificationDomain;
import tech.brito.ead.notificationhex.core.domain.PageInfo;
import tech.brito.ead.notificationhex.core.domain.enums.NotificationStatus;
import tech.brito.ead.notificationhex.core.ports.NotificationPersistencePort;
import tech.brito.ead.notificationhex.core.ports.NotificationServicePort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {
    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Transactional
    public NotificationDomain save(NotificationDomain notificationDomain) {
        return notificationPersistencePort.save(notificationDomain);
    }

    public List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo) {
        return notificationPersistencePort.findAllNotificationsByUserAndStatus(userId, NotificationStatus.CREATED, pageInfo);
    }

    public Optional<NotificationDomain> findByIdAndUserId(UUID notificationId, UUID userId) {
        return notificationPersistencePort.findByIdAndUserId(notificationId, userId);
    }
}
