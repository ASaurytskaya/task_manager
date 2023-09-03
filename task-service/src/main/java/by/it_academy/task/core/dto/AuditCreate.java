package by.it_academy.task.core.dto;

public class AuditCreate {

    private String text;

    private UserSimpleViewWithId user;

    private String type;

    private String id;

    public AuditCreate() {
    }

    public AuditCreate(String text, UserSimpleViewWithId user, String type, String id) {
        this.text = text;
        this.user = user;
        this.type = type;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserSimpleViewWithId getUser() {
        return user;
    }

    public AuditCreate setUser(UserSimpleViewWithId user) {
        this.user = user;
        return this;
    }
}
