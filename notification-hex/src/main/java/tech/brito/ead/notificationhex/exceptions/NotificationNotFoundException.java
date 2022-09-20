package tech.brito.ead.notificationhex.exceptions;

public class NotificationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotificationNotFoundException(String message) {
        super(message);
    }

    public NotificationNotFoundException() {
        this("Notification not found!");
    }
}
