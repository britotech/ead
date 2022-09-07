package tech.brito.ead.notification.domain.exceptions;

public class ValidationJwtException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationJwtException(String message) {
        super(message);
    }

    public ValidationJwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
