package by.it_academy.user.service;


import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    private final MailSender mailSender;

    public MailSenderService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(String mail, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Confirm mail address");
        message.setText(text);
        //ToDo variable from
        message.setFrom("task.manager.23.01@gmail.com");
        try{
            mailSender.send(message);
        } catch(MailException e) {
            //TODO exception for invalid mail
            throw new RuntimeException(e.getMessage());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
