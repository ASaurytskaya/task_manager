package by.it_academy.user.service.exceptions;

public class CustomMailSenderException extends RuntimeException {

    public CustomMailSenderException(String message) {
        super(message);
    }
}
