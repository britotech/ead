package tech.brito.ead.course.api.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.brito.ead.course.domain.exceptions.DomainRuleException;
import tech.brito.ead.course.domain.exceptions.EntityInUseException;
import tech.brito.ead.course.domain.exceptions.EntityNotFoundException;
import tech.brito.ead.course.domain.exceptions.SubscriptionAlreadyExistsException;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        if (isNull(body)) {
            body = createProblemBuilder(status).build();
        } else if (body instanceof String bodyString) {
            body = createProblemBuilder(status, bodyString).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status) {
        return createProblemBuilder(status, status.getReasonPhrase());
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, String title) {
        return Problem
                .builder()
                .title(title)
                .status(status.value())
                .userMessage(MessageExceptionHandler.MSG_INTERNAL_ERROR)
                .timestamp(OffsetDateTime.now());
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return createProblemBuilder(status, problemType, detail, MessageExceptionHandler.MSG_INTERNAL_ERROR);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail, String userMessage) {
        return Problem
                .builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(userMessage)
                .timestamp(OffsetDateTime.now());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {

        var detail = String.format(MessageExceptionHandler.MSG_RESOURCE_NOT_FOUND, ex.getRequestURL());
        var problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        var rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException invalidEx) {
            return handleInvalidFormat(invalidEx, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException propertyEx) {
            return handlePropertyBinding(propertyEx, headers, status, request);
        }

        var problem = createProblemBuilder(HttpStatus.BAD_REQUEST,
                                           ProblemType.INCOMPREHENSIBLE_MESSAGE,
                                           MessageExceptionHandler.MSG_INVALID_BODY).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    public ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                      HttpHeaders headers,
                                                      HttpStatus status,
                                                      WebRequest request) {

        var path = joinPatch(ex);
        var detail =
                String.format(MessageExceptionHandler.MSG_PROPERTY_INVALID_TYPE, path, ex.getValue(), ex.getTargetType().getSimpleName());
        var problem = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemType.INCOMPREHENSIBLE_MESSAGE, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPatch(MismatchedInputException ex) {
        return ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }

    public ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {

        var path = joinPatch(ex);
        var detail = String.format(MessageExceptionHandler.MSG_PROPERTY_NOT_RECOGNIZED, path, ex.getReferringClass());
        var problem = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemType.INCOMPREHENSIBLE_MESSAGE, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        var fieldErrors = ex.getBindingResult().getFieldErrors();
        var problemFields = fieldErrors.stream().map(fieldError -> createProblemField(fieldError)).collect(Collectors.toList());

        var problem = createProblemBuilder(HttpStatus.BAD_REQUEST,
                                           ProblemType.INVALID_DATA,
                                           MessageExceptionHandler.MSG_INVALID_PROPERTY,
                                           MessageExceptionHandler.MSG_INVALID_PROPERTY).fields(problemFields).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private Problem.Field createProblemField(FieldError fieldError) {
        return Problem.Field.builder().name(fieldError.getField()).userMessage(fieldError.getDefaultMessage()).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {

        var type = ex.getRequiredType();
        String detail;

        if (type.isEnum()) {
            detail = String.format(MessageExceptionHandler.MSG_ENUM_PARAMETER_INVALID_TYPE,
                                   ex.getName(),
                                   ex.getValue(),
                                   StringUtils.join(type.getEnumConstants(), ", "));
        } else {
            detail = String.format(MessageExceptionHandler.MSG_PARAMETER_INVALID_TYPE, ex.getName(), ex.getValue(), type.getSimpleName());
        }

        var problem = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemType.INVALID_PARAMETER, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        log.error("handleUncaught ->", ex);
        var problem = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR,
                                           ProblemType.SYSTEM_FAILURE,
                                           MessageExceptionHandler.MSG_INTERNAL_ERROR).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DomainRuleException.class)
    public ResponseEntity<?> handleDomainRule(DomainRuleException ex, WebRequest request) {
        var problem = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemType.BUSINESS_RULE_VIOLATION, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        var problem = createProblemBuilder(HttpStatus.NOT_FOUND, ProblemType.RESOURCE_NOT_FOUND, ex.getMessage(), ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUse(EntityInUseException ex, WebRequest request) {
        var problem = createProblemBuilder(HttpStatus.CONFLICT, ProblemType.ENTITY_IN_USE, ex.getMessage(), ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    public ResponseEntity<?> handleSubscriptionAlreadyExists(SubscriptionAlreadyExistsException ex, WebRequest request) {
        var problem = createProblemBuilder(HttpStatus.CONFLICT,
                                           ProblemType.SUBSCRIPTION_ALREADY_EXISTS,
                                           ex.getMessage(),
                                           ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
