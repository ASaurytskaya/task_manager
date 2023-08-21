package by.it_academy.task.service.exception;

public class ProjectNotFoundException extends TaskServiceException {


    public ProjectNotFoundException(String message, String field) {
        super(message, field);
    }

}
