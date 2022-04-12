package tech.brito.ead.authuser.domain.exceptions;

public class EntityInUseException extends DomainRuleException {
	private static final long serialVersionUID = 1L;

	public EntityInUseException(String mensagem) {
		super(mensagem);
	}
}
