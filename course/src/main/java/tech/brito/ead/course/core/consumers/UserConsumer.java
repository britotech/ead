package tech.brito.ead.course.core.consumers;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tech.brito.ead.course.api.models.UserEventDTO;
import tech.brito.ead.course.domain.models.User;
import tech.brito.ead.course.domain.services.UserService;
import tech.brito.ead.course.enums.ActionType;

@Component
public class UserConsumer {

    private final ModelMapper modelMapper;

    private final UserService userService;

    public UserConsumer(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
                                             exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}",
                                                                  type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenUserEvent(@Payload UserEventDTO userEventDTO) {
        var user = modelMapper.map(userEventDTO, User.class);
        var actionType = ActionType.valueOf(userEventDTO.getActionType());
        if (actionType.isDelete()) {
            userService.delete(user.getId());
            return;
        }

        userService.save(user);
    }
}
