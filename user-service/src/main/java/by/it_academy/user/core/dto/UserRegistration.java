package by.it_academy.user.core.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegistration {

    @NotBlank(message = "Укажите адрес почты")
    @Email(message = "Укажите валидный адрес почты")
    private String mail;

    @NotBlank(message = "Укажите ФИО")
    private String  fio;

    @NotBlank(message = "Укажите пароль")
    private String password;

    public UserRegistration() {
    }

    public UserRegistration(String mail, String fio, String password) {
        this.mail = mail;
        this.fio = fio;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
