package by.it_academy.report.core;

public enum ReportType {
    JOURNAL_AUDIT ("JOURNAL_AUDIT");

    private String value;

    ReportType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
