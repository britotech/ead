package tech.brito.ead.course.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    INVALID_DATA("/invalid-data", "Invalid data"), SYSTEM_FAILURE("/system-failure", "System failure"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"), ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_RULE_VIOLATION("/business-rule-violation", "Business rule violation");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://brito-ead.com" + path;
        this.title = title;
    }
}
