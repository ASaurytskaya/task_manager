package by.it_academy.user.service.exceptions;

public class ActivationCodeInvalidException extends RuntimeException{

    public ActivationCodeInvalidException(String message) {
        super(message);
    }
}
