package by.it_academy.user.service;


import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailSenderService {
    private static final String WELCOME_TEXT =
            "Добро пожаловать в TASK MANAGER!\nДля продолжения регистрации перейдите по следующей ссылке: \n%s";
    private final MailSender mailSender;

    public MailSenderService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async()
    public void sendMessage(String mail, UUID code) {
        String link  = String.format("localhost:80/api/v1/users/verification?code=%s&mail=%s", code, mail);
        String text = String.format(WELCOME_TEXT, link);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Confirm mail address");
        message.setText(text);
        message.setFrom("task.manager.23.01@gmail.com");
        mailSender.send(message);
    }
}
