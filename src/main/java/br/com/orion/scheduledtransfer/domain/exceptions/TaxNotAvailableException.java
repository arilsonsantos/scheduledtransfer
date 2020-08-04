package br.com.orion.scheduledtransfer.domain.exceptions;

public class TaxNotAvailableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TaxNotAvailableException(String message) {
        super(message);
    }
}
