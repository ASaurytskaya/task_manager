package by.it_academy.task.service;

import by.it_academy.task.service.exception.ProjectNotFoundException;
import by.it_academy.task.service.exception.TaskServiceException;
import by.it_academy.task.util.errors.ErrorType;
import by.it_academy.task.util.errors.TErrorResponse;
import by.it_academy.task.util.errors.TStructuredErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class TaskServiceExceptionHandler {
    private static final String STATUS_400 = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз.";
    private static final String STATUS_500 = "Внутренняя ошибка сервера. Сервер не смог корректно обработать запрос.";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequestError() {

        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, STATUS_400);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(TaskServiceException.class)
    public ResponseEntity<?> handleUserServiceError(TaskServiceException e) {
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
    public ResponseEntity<?> handleAnyError() {
        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, STATUS_500);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(500));
    }

}
