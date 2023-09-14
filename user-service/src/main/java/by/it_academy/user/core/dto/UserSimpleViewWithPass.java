package by.it_academy.user.core.dto;

import by.it_academy.user.core.UserRole;
import by.it_academy.user.core.UserStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserSimpleViewWithPass {
    @NotBlank
    private final String mail;

    @NotBlank
    private final String  fio;

    @NotNull
    @JsonProperty("role")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserRole userRole;

    @NotNull
    @JsonProperty("status")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserStatus userStatus;

    @NotNull
    private final String password;

    @JsonCreator
    private UserSimpleViewWithPass(UserSimpleViewWithPassBuilder builder) {
        this.mail = builder.mail;
        this.fio = builder.fio;
        this.userRole = builder.userRole;
        this.userStatus = builder.userStatus;
        this.password = builder.password;
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

    public String getPassword() {
        return password;
    }

    @JsonDeserialize
    public static class UserSimpleViewWithPassBuilder {
        private String mail;

        private String  fio;

        private UserRole userRole;

        private UserStatus userStatus;

        private String password;

        public UserSimpleViewWithPassBuilder() {
        }

        public UserSimpleViewWithPassBuilder(String mail, String fio, UserRole userRole, UserStatus userStatus, String password) {
            this.mail = mail;
            this.fio = fio;
            this.userRole = userRole;
            this.userStatus = userStatus;
            this.password = password;
        }

        public UserSimpleViewWithPassBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserSimpleViewWithPassBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserSimpleViewWithPassBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserSimpleViewWithPassBuilder setUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public UserSimpleViewWithPassBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserSimpleViewWithPass build() {
            return new UserSimpleViewWithPass(this);
        }


    }
}
