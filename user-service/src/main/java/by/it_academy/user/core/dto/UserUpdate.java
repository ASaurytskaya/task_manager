package by.it_academy.user.core.dto;

import by.it_academy.user.core.UserRole;
import by.it_academy.user.core.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdate {
    @NotBlank(message = "Укажите адрес почты")
    @Email(message = "Укажите валидный адрес почты")
    private String mail;

    @NotBlank(message = "Укажите адрес почты")
    private String  fio;

    @NotBlank(message = "Укажите роль пользователя")
    private UserRole userRole;

    @NotBlank(message = "Укажите статус пользователя")
    private UserStatus userStatus;

    @NotBlank(message = "Укажите пароль")
    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;

    public UserUpdate() {
    }

    public UserUpdate(String mail, String fio, UserRole userRole, UserStatus userStatus, String password) {
        this.mail = mail;
        this.fio = fio;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.password = password;
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


    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
