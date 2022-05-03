package tech.brito.ead.authuser.api.models;

import lombok.Data;
import tech.brito.ead.authuser.enums.CourseLevel;
import tech.brito.ead.authuser.enums.CourseStatus;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CourseDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private CourseLevel level;
    private CourseStatus status;
    private UUID userInstructor;
}
