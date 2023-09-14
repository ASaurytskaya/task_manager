package by.it_academy.user.core.dto;

import by.it_academy.user.core.UserRole;
import by.it_academy.user.core.UserStatus;
import com.fasterxml.jackson.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserView {

    private final UUID uuid;

    @JsonProperty("dt_create")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime dtUpdate;

    private final String mail;

    private final String  fio;

    @JsonProperty("role")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserRole userRole;

    @JsonProperty("status")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserStatus userStatus;

    private UserView(UserViewBuilder builder) {
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

    public static class UserViewBuilder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String mail;
        private String  fio;
        private UserRole userRole;
        private UserStatus userStatus;

        public UserViewBuilder() {}

        public UserViewBuilder(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String mail, String fio, UserRole userRole, UserStatus userStatus) {
            this.uuid = uuid;
            this.dtCreate = dtCreate;
            this.dtUpdate = dtUpdate;
            this.mail = mail;
            this.fio = fio;
            this.userRole = userRole;
            this.userStatus = userStatus;
        }

        public UserViewBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public UserViewBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public UserViewBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public UserViewBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserViewBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserViewBuilder setUserRole(String userRole) {
            this.userRole = UserRole.valueOf(userRole);
            return this;
        }

        public UserViewBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserViewBuilder setUserStatus(String userStatus) {
            this.userStatus = UserStatus.valueOf(userStatus);
            return this;
        }

        public UserViewBuilder setUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public UserView build() {
            return new UserView(this);
        }
    }
}
