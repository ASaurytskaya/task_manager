package by.it_academy.task.service.exception;

public class TaskNotFoundException extends TaskServiceException {


    public TaskNotFoundException(String message, String field) {
        super(message, field);
    }

}
