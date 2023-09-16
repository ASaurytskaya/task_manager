package by.it_academy.report.core;

public enum ReportStatus {
    LOADED ("LOADED"),
    PROGRESS ("PROGRESS"),
    ERROR ("ERROR"),
    DONE ("DONE");

    private final String status;

    ReportStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
