package by.it_academy.task.service.exception;

public class TaskVersionException extends TaskServiceException {


    public TaskVersionException(String message, String field) {
        super(message, field);
    }

}
