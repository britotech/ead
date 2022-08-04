package tech.brito.ead.course.domain.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class SubscriptionDto implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotNull
    private UUID userId;
}
