package tech.brito.ead.notificationhex.adapters.dtos;

import lombok.Data;
import tech.brito.ead.notificationhex.core.domain.enums.NotificationStatus;

import javax.validation.constraints.NotNull;

@Data
public class NotificationDTO {

    @NotNull
    private NotificationStatus status;
}
