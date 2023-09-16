package by.it_academy.report.service;

import by.it_academy.report.service.exception.ReportNotFoundException;
import by.it_academy.report.service.exception.ReportServiceException;
import by.it_academy.report.util.errors.ErrorType;
import by.it_academy.report.util.errors.TErrorResponse;
import by.it_academy.report.util.errors.TStructuredErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

@ControllerAdvice
public class ReportServiceExceptionHandler {
    private static final String STATUS_400 = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз.";
    private static final String STATUS_500 = "Внутренняя ошибка сервера. Сервер не смог корректно обработать запрос.";
    private static final String STATUS_204 = "Сервер, по предоставленному uuid, не смог найти информацию.";


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequestError() {

        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, STATUS_400);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<?> handleReportNotFoundError() {
        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, STATUS_204);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(204));
    }

    @ExceptionHandler(ReportServiceException.class)
    public ResponseEntity<?> handleReportServiceError(ReportServiceException e) {
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
        TErrorResponse errorResponse = new TErrorResponse(ErrorType.ERROR, STATUS_500);
        return new ResponseEntity<>(errorResponse + "\n" + e.getMessage(), HttpStatus.valueOf(500));
    }

}
