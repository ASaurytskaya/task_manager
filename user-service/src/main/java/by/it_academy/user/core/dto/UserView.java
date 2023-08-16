package by.it_academy.user.core.dto;

import by.it_academy.user.core.UserRole;
import by.it_academy.user.core.UserStatus;
import com.fasterxml.jackson.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserView {

    private final UUID uuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime dtCreate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime dtUpdate;

    private final String mail;

    private final String  fio;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserRole userRole;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserStatus userStatus;

    private UserView(UserBuilder builder) {
        this.uuid = builder.uuid;
        this.dtCreate = builder.dtCreate;
        this.dtUpdate = builder.dtUpdate;
        this.mail = builder.mail;
        this.fio = builder.fio;
        this.userRole = builder.userRole;
        this.userStatus = builder.userStatus;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public String getMail() {
        return mail;
    }

    public String getFio() {
        return fio;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public static class UserBuilder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String mail;
        private String  fio;
        private UserRole userRole;
        private UserStatus userStatus;

        public UserBuilder() {}

        public UserBuilder(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String mail, String fio, UserRole userRole, UserStatus userStatus) {
            this.uuid = uuid;
            this.dtCreate = dtCreate;
            this.dtUpdate = dtUpdate;
            this.mail = mail;
            this.fio = fio;
            this.userRole = userRole;
            this.userStatus = userStatus;
        }

        public UserBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public UserBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public UserBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public UserBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserBuilder setUserRole(String userRole) {
            this.userRole = UserRole.valueOf(userRole);
            return this;
        }

        public UserBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserBuilder setUserStatus(String userStatus) {
            this.userStatus = UserStatus.valueOf(userStatus);
            return this;
        }

        public UserBuilder setUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public UserView build() {
            return new UserView(this);
        }
    }
}
