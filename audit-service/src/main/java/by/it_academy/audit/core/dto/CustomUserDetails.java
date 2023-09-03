package by.it_academy.audit.core.dto;


import by.it_academy.audit.core.UserRole;
import by.it_academy.audit.core.UserStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final UUID id;

    private final String mail;

    private final String  fio;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserRole userRole;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final UserStatus userStatus;

    private final String password;

    @JsonCreator
    private CustomUserDetails(CustomUserDetailsBuilder builder) {
        this.id = builder.id;
        this.mail = builder.mail;
        this.fio = builder.fio;
        this.userRole = builder.userRole;
        this.userStatus = builder.userStatus;
        this.password = builder.password;
    }

    public UUID getId() {
        return id;
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
    public static class CustomUserDetailsBuilder {

        private UUID id;

        private String mail;

        private String  fio;

        private UserRole userRole;

        private UserStatus userStatus;

        private String password;

        public CustomUserDetailsBuilder() {
        }

        public UUID getId() {
            return id;
        }

        public CustomUserDetailsBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public String getMail() {
            return mail;
        }

        public CustomUserDetailsBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public String getFio() {
            return fio;
        }

        public CustomUserDetailsBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserRole getUserRole() {
            return userRole;
        }

        public CustomUserDetailsBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserStatus getUserStatus() {
            return userStatus;
        }

        public CustomUserDetailsBuilder setUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public CustomUserDetailsBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public CustomUserDetails build() {
            return new CustomUserDetails(this);
        }
    }
}
