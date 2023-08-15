package by.it_academy.user.core.dto;

public class MailVerification {
    private String mail;
    private String code;

    public MailVerification() {
    }

    public MailVerification(String mail, String code) {
        this.mail = mail;
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public MailVerification setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getCode() {
        return code;
    }

    public MailVerification setCode(String code) {
        this.code = code;
        return this;
    }
}
