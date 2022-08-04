package tech.brito.ead.course.domain.models;

import lombok.Data;
import tech.brito.ead.course.enums.CourseLevel;
import tech.brito.ead.course.enums.CourseStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Data
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    @Size(min = 4, max = 100)
    private String name;

    @NotBlank
    @Size(min = 4, max = 255)
    private String description;

    @Size(max = 255)
    private String imageUrl;

    @NotNull
    private CourseLevel level;

    @NotNull
    private CourseStatus status;

    @NotNull
    private UUID userInstructor;
}
