package tech.brito.ead.course.domain.exceptions;

import java.util.UUID;

public class ModuleNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public ModuleNotFoundException(String message) {
        super(message);
    }

    public ModuleNotFoundException()
    {
        this("Module not found for this course.");
    }
}
