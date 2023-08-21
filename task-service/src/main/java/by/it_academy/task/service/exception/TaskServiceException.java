package by.it_academy.task.service.exception;

public class TaskServiceException extends IllegalArgumentException {
    private final String field;

    public TaskServiceException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
