package by.it_academy.audit.core.dto;

import by.it_academy.audit.core.EssenceType;

public class AuditCreate {
    private User user;

    private String text;

    private EssenceType type;

    private String id;

    public AuditCreate() {
    }

    public AuditCreate(User user, String text, EssenceType type, String id) {
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EssenceType getType() {
        return type;
    }

    public void setType(EssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public AuditCreate setUser(User user) {
        this.user = user;
        return this;
    }
}
