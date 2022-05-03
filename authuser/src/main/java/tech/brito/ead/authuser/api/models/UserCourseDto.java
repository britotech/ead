package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class UserCourseDto implements Serializable {

    private static final long serialVersionUID = 1l;

    private UUID id;

    private UUID userId;

    @NotNull
    private UUID courseId;
}
