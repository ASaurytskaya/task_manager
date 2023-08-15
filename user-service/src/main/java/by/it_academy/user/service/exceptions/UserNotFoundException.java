package by.it_academy.user.service.exceptions;

public class UserNotFoundException extends UserServiceException {
    private final String field;

    public UserNotFoundException(String message, String field) {
        super(message, field);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
