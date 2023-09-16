package by.it_academy.report.core.dto;


import by.it_academy.report.core.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.UUID;

public class User {
    private  UUID uuid;

    private String mail;

    private String  fio;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserRole userRole;

    public User() {
    }

    public User(UUID uuid, String mail, String fio, UserRole userRole) {
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
