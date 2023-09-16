package by.it_academy.report.service.exception;

public class ReportServiceException extends IllegalArgumentException {
    private final String field;

    public ReportServiceException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
