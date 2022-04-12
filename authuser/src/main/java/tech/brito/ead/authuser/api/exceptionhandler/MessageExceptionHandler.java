package tech.brito.ead.authuser.api.exceptionhandler;

public class MessageExceptionHandler {

    public static String MSG_INVALID_BODY = "The request body is invalid. Check syntax error.";
    public static String MSG_PROPERTY_NOT_RECOGNIZED = "Property '%s' is not recognized for model '%s'";
    public static String MSG_RESOURCE_NOT_FOUND = "The resource %s you tried to access does not exist.";
    public static String MSG_INVALID_PROPERTY = "One or more fields are invalid. Please fill in correctly and try again.";

    public static String MSG_INTERNAL_ERROR = """
            An unexpected internal system error has occurred. Please try again and if the problem persists, contact your system administrator.""";

    public static String MSG_PROPERTY_INVALID_TYPE = """
            The property was given the value '%s' which is an invalid type. Correction to a value compatible with the type %s.""";

    public static String MSG_PARAMETER_INVALID_TYPE = """
            The URL parameter '%s' received the value '%s' which is an invalid type. Correction to a value compatible with type %s.""";

    public static String MSG_ENUM_PARAMETER_INVALID_TYPE = """
            The URL parameter '%s' received the value '%s' which is an invalid type. Correction to a value between possible (%s).""";
}
