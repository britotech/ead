package tech.brito.ead.course.domain.exceptions;

public class CourseNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException() {
        this("Course not found.");
    }
}
