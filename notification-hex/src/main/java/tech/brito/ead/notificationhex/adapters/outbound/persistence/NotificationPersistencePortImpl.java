package tech.brito.ead.notificationhex.adapters.outbound.persistence;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import tech.brito.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import tech.brito.ead.notificationhex.core.domain.NotificationDomain;
import tech.brito.ead.notificationhex.core.domain.PageInfo;
import tech.brito.ead.notificationhex.core.domain.enums.NotificationStatus;
import tech.brito.ead.notificationhex.core.ports.NotificationPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain save(NotificationDomain notificationDomain) {
        var notificationEntity = notificationJpaRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(notificationEntity, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllNotificationsByUserAndStatus(UUID userId, NotificationStatus status, PageInfo pageInfo) {
        var pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        var notifications = notificationJpaRepository.findAllByUserIdAndStatus(userId, status, pageable);
        return notifications.stream().map(e -> modelMapper.map(e, NotificationDomain.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationDomain> findByIdAndUserId(UUID notificationId, UUID userId) {
        var notificationEntityOptional = notificationJpaRepository.findByIdAndUserId(notificationId, userId);
        if (notificationEntityOptional.isPresent()) {
            return Optional.of(modelMapper.map(notificationEntityOptional.get(), NotificationDomain.class));
        }

        return Optional.empty();
    }
}
