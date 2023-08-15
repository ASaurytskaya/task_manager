package by.it_academy.user.service.exceptions;

public class UserServiceException extends IllegalArgumentException {
    private final String field;

    public UserServiceException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
