package by.it_academy.user.core.dto;

import by.it_academy.user.core.UserRole;
import by.it_academy.user.core.UserStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserSimleViewWithPass implements UserDetails {
    @NotBlank
    private final String mail;

    @NotBlank
    private final String  fio;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserRole userRole;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserStatus userStatus;

    @NotNull
    private final String password;

    @JsonCreator
    private UserSimleViewWithPass(UserSimpleViewWithPassBuilder builder) {
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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(getUserRole());
        list.add(getUserStatus());
        return list;
    }

    @Override
    public String getUsername() {
        return getMail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

        public UserSimleViewWithPass build() {
            return new UserSimleViewWithPass(this);
        }


    }
}
