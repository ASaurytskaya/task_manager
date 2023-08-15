package by.it_academy.user.service;

import by.it_academy.user.service.exceptions.UserServiceException;
import by.it_academy.user.util.errors.ErrorType;
import by.it_academy.user.util.errors.TErrorResponse;
import by.it_academy.user.util.errors.TStructuredErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequestError() {

        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз.");
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<?> handleUserServiceError(UserServiceException e) {
        TStructuredErrorResponse errorResponse = new TStructuredErrorResponse(ErrorType.STRUCTURED_ERROR, new HashMap<>());
        errorResponse.addError(e.getMessage(), e.getField());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleInvalidConstrainError(ConstraintViolationException e) {
        TStructuredErrorResponse errorResponse = new TStructuredErrorResponse(ErrorType.STRUCTURED_ERROR, new HashMap<>());
        e.getConstraintViolations().forEach(constraintViolation -> {
            errorResponse.addError(constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString());
        });

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(400));
    }

    @ExceptionHandler({Exception.class, Error.class})
    public ResponseEntity<?> handleAnyError(Exception e) {
        //Todo remove print
        System.out.println(e.getMessage());
        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, "Внутренняя ошибка сервера. Сервер не смог корректно обработать запрос.");
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(500));
    }

}
