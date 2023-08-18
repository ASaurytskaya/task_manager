package by.it_academy.user.core.dto;

import java.util.UUID;

public class MailVerification {
    private String mail;
    private UUID code;

    public MailVerification() {
    }

    public MailVerification(String mail, UUID code) {
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

    public UUID getCode() {
        return code;
    }

    public MailVerification setCode(UUID code) {
        this.code = code;
        return this;
    }
}
