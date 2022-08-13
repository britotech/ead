package tech.brito.ead.course.domain.exceptions;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(UUID id) {
        this(String.format("User not found for ID:%s", id));
    }
}