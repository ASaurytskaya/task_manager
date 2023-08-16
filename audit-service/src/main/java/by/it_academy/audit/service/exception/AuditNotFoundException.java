package by.it_academy.audit.service.exception;

public class AuditNotFoundException extends RuntimeException{
    private String message;
    private String field;

    public AuditNotFoundException(String message, String field) {
        super(message);
        this.message = message;
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
