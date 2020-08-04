package br.com.orion.scheduledtransfer.domain.exceptions;

public class AccountNumberFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountNumberFormatException(String message) {
        super(message);
    }
}
