package by.it_academy.task.core;

public enum TaskStatus {
    WAIT ("WAIT"),
    BLOCK ("BLOCK"),
    IN_WORK ("IN_WORK"),
    DONE ("DONE"),
    CLOSE ("CLOSE");

    private String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
