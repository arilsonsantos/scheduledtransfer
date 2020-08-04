package br.com.orion.scheduledtransfer.application.exceptionhandler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorDetail extends  ErrorDetail {

    private Map<String, String> errors;

}