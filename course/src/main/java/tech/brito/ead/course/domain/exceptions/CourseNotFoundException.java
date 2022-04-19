package tech.brito.ead.course.domain.exceptions;

import java.util.UUID;

public class CourseNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(UUID id) {
        this(String.format("Course not found for ID:%s", id));
    }
}
