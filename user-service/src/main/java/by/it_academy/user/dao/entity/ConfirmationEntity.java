package by.it_academy.user.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmation")
public class ConfirmationEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "mail")
    private String mail;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @NotNull
    @Column(name = "code")
    private UUID code;

    public ConfirmationEntity() {
    }

    public ConfirmationEntity(UUID id, String mail, LocalDateTime dtCreate, UUID code) {
        this.id = id;
        this.mail = mail;
        this.dtCreate = dtCreate;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }
}
