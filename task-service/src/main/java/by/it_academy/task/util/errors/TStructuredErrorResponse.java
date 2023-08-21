package by.it_academy.task.util.errors;

import java.util.Map;

public class TStructuredErrorResponse {
    private ErrorType logref;
    private Map<String, String> errors;

    public TStructuredErrorResponse() {
    }

    public TStructuredErrorResponse(ErrorType errorType, Map<String, String> errors) {
        this.logref = errorType;
        this.errors = errors;
    }

    public ErrorType getLogref() {
        return logref;
    }

    public void setLogref(ErrorType errorType) {
        this.logref = errorType;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addError(String message, String field) {
        this.errors.put(message, field);
    }
}
