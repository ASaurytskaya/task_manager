package by.it_academy.task.core;

public enum ProjectStatus {
    ARCHIVED ("ARCHIVED"),
    ACTIVE ("ACTIVE");

    private String status;

    ProjectStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
