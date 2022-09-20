package tech.brito.ead.notificationhex.adapters.inbound.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tech.brito.ead.notificationhex.adapters.dtos.NotificationCommandDTO;
import tech.brito.ead.notificationhex.core.domain.NotificationDomain;
import tech.brito.ead.notificationhex.core.domain.enums.NotificationStatus;
import tech.brito.ead.notificationhex.core.ports.NotificationServicePort;

@Component
public class NotificationConsumer {

    private final NotificationServicePort notificationServicePort;

    public NotificationConsumer(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
                                             exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}",
                                                                  type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
                                             key = "${ead.broker.key.notificationCommandKey}"))
    public void listen(@Payload NotificationCommandDTO notificationCommandDTO) {
        var notification = new NotificationDomain();
        BeanUtils.copyProperties(notificationCommandDTO, notification);
        notification.setStatus(NotificationStatus.CREATED);

        notificationServicePort.save(notification);
    }
}
