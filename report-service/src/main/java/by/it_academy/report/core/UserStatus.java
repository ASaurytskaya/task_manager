package by.it_academy.report.core;

import org.springframework.security.core.GrantedAuthority;

public enum UserStatus implements GrantedAuthority {
    WAITING_ACTIVATION ("WAITING_ACTIVATION"),
    ACTIVATED ("ACTIVATED"),
    DEACTIVATED ("DEACTIVATED");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    @Override
    public String getAuthority() {
        return status;
    }
}
