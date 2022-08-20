package tech.brito.ead.notification.dtos;

import lombok.Data;
import tech.brito.ead.notification.enums.NotificationStatus;

import javax.validation.constraints.NotNull;

@Data
public class NotificationDTO {

    @NotNull
    private NotificationStatus status;
}
