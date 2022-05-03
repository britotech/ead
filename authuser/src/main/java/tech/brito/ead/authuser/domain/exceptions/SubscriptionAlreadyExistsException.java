package tech.brito.ead.authuser.domain.exceptions;

public class SubscriptionAlreadyExistsException extends DomainRuleException{

    private static final long serialVersionUID = 1L;

    public SubscriptionAlreadyExistsException(String message) {
        super(message);
    }

    public SubscriptionAlreadyExistsException() {
        this("Subscription already exists.");
    }
}
