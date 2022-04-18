package tech.brito.ead.course.api.models;

import lombok.Data;
import tech.brito.ead.course.domain.enums.CourseLevel;
import tech.brito.ead.course.domain.enums.CourseStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseLevel level;

    @NotNull
    private CourseStatus status;

    @NotNull
    private UUID userInstructor;
}
