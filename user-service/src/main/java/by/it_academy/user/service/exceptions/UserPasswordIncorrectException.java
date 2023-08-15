package by.it_academy.user.service.exceptions;

public class UserPasswordIncorrectException extends UserServiceException {
    private final String field;

    public UserPasswordIncorrectException(String message, String field) {
        super(message, field);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
