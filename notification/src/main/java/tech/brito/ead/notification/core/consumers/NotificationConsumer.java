package tech.brito.ead.notification.core.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tech.brito.ead.notification.domain.models.Notification;
import tech.brito.ead.notification.domain.services.NotificationService;
import tech.brito.ead.notification.dtos.NotificationCommandDTO;
import tech.brito.ead.notification.enums.NotificationStatus;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
                                             exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}",
                                                                  type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
                                             key = "${ead.broker.key.notificationCommandKey}"))
    public void listen(@Payload NotificationCommandDTO notificationCommandDTO) {
        var notification = new Notification();
        BeanUtils.copyProperties(notificationCommandDTO, notification);
        notification.setStatus(NotificationStatus.CREATED);

        notificationService.save(notification);
    }
}
