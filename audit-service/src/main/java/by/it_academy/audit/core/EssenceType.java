package by.it_academy.audit.core;

public enum EssenceType {
    USER ("USER"),
    PROJECT("PROJECT"),
    TASK("TASK");

    private String value;

    EssenceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
