package br.com.orion.scheduledtransfer.application.exceptionhandler;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

    private int statusCode;

    private String message;

    private String exception;

    private LocalDateTime timestamp;

}