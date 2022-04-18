package tech.brito.ead.course.domain.exceptions;

public class EntityInUseException extends DomainRuleException {
	private static final long serialVersionUID = 1L;

	public EntityInUseException(String mensagem) {
		super(mensagem);
	}
}
