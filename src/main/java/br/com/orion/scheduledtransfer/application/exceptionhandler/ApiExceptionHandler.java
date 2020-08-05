package br.com.orion.scheduledtransfer.application.exceptionhandler;

import br.com.orion.scheduledtransfer.application.utils.MessageUtils;
import br.com.orion.scheduledtransfer.domain.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import static br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum.INVALID_ARGUMENT;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageUtils messageUtils;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        ValidationErrorDetail error = new ValidationErrorDetail();
        error.setStatusCode(status.value());
        error.setException(ex.getClass().getSimpleName());
        error.setMessage(messageUtils.getMessage(INVALID_ARGUMENT));
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(UserNotFoundException ex) {

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
    public ResponseEntity<?> handleAccountNotFoundException(AccountNotFoundException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<?> handleInvalidDateException(InvalidDateException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNumberFormatException.class)
    public ResponseEntity<?> handleAccountNumberFormatException(AccountNumberFormatException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}