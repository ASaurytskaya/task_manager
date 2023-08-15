package by.it_academy.user.service.exceptions;

import by.it_academy.user.service.exceptions.UserServiceException;

public class TokenInvalidException extends RuntimeException{

    public TokenInvalidException(String message) {
        super(message);
    }
}
