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
    private String mail;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @NotNull
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

    public ConfirmationEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public ConfirmationEntity setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public ConfirmationEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public UUID getCode() {
        return code;
    }

    public ConfirmationEntity setCode(UUID code) {
        this.code = code;
        return this;
    }
}
