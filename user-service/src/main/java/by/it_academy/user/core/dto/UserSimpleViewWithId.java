package by.it_academy.user.core.dto;

import by.it_academy.user.core.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserSimpleViewWithId {
    private  UUID uuid;

    private String mail;

    private String  fio;

    @JsonProperty("role")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserRole userRole;

    public UserSimpleViewWithId() {
    }

    public UserSimpleViewWithId(UUID uuid, String mail, String fio, UserRole userRole) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.userRole = userRole;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
