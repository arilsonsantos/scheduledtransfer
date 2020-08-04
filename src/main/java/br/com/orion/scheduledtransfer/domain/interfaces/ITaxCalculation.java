package br.com.orion.scheduledtransfer.domain.interfaces;

import br.com.orion.scheduledtransfer.domain.model.Transfer;

import java.math.BigDecimal;

public interface ITaxCalculation {

    BigDecimal calculate(Transfer transaction);
}
