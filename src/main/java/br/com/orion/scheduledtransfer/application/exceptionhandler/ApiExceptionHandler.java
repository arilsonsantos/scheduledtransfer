package br.com.orion.scheduledtransfer.application.exceptionhandler;

import br.com.orion.scheduledtransfer.domain.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        ValidationErrorDetail error = new ValidationErrorDetail();
        error.setStatusCode(status.value());
        error.setException(ex.getClass().getSimpleName());
        error.setMessage("Invalid argument(s)");
        error.setTimestamp(LocalDateTime.now());
        error.setErrors(errors);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceNotFoundException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaxNotAvailableException.class)
    public ResponseEntity<?> handleResourceNotFoundException(TaxNotAvailableException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(AccountNotFoundException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<?> handleResourceNotFoundException(InvalidDateException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}