package by.it_academy.task.service.exception;

public class UserNotFoundException extends TaskServiceException {


    public UserNotFoundException(String message, String field) {
        super(message, field);
    }

}
