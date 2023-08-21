package by.it_academy.task.util.errors;

public class TErrorResponse {

    private ErrorType logref;
    private String message;

    public TErrorResponse() {
    }

    public TErrorResponse(ErrorType errorType, String message) {
        this.logref = errorType;
        this.message = message;
    }

    public ErrorType getLogref() {
        return logref;
    }

    public void setLogref(ErrorType errorType) {
        this.logref = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
