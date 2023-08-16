package by.it_academy.user.core;

public enum EssenceType {
    USER ("USER");

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
