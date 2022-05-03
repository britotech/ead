package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class InstructorDto implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotNull
    private UUID userId;
}
