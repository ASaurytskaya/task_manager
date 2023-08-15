package by.it_academy.user.service.exceptions;

public class UserMailDuplicateException extends UserServiceException{
    private final String field;

    public UserMailDuplicateException(String message, String field) {
        super(message, field);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
