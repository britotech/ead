package tech.brito.ead.notification.domain.exceptions;

public class NotificationNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public NotificationNotFoundException(String message) {
        super(message);
    }

    public NotificationNotFoundException() {
        this("Notification not found!");
    }
}
