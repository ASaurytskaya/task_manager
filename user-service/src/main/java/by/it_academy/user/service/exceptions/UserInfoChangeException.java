package by.it_academy.user.service.exceptions;

public class UserInfoChangeException extends UserServiceException{
    private final String field;

    public UserInfoChangeException(String message, String field) {
        super(message, field);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
