package tech.brito.ead.course.domain.exceptions;

public class EntityNotFoundException extends DomainRuleException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String mensagem) {
		super(mensagem);
	}
}
