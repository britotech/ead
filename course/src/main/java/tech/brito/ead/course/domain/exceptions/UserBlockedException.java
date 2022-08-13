package tech.brito.ead.course.domain.exceptions;

public class UserBlockedException extends DomainRuleException {
    private static final long serialVersionUID = 1L;

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException() {
        this("User is blocked");
    }
}
