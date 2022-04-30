package tech.brito.ead.course.domain.exceptions;

public class LessonNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public LessonNotFoundException(String message) {
        super(message);
    }

    public LessonNotFoundException() {
        this("Lesson not found for this module.");
    }
}
