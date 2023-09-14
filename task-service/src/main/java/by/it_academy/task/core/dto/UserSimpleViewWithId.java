package by.it_academy.task.core.dto;

import by.it_academy.task.core.UserRole;
import by.it_academy.task.core.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.UUID;

public class UserSimpleViewWithId {
    private  UUID uuid;

    private String mail;

    private String  fio;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserRole userRole;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserStatus userStatus;
    public UserSimpleViewWithId() {
    }

    public UserSimpleViewWithId(UUID uuid, String mail, String fio, UserRole userRole, UserStatus userStatus) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.userRole = userRole;
        this.userStatus = userStatus;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
