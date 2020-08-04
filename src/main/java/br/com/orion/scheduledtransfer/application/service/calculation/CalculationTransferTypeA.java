package br.com.orion.scheduledtransfer.application.service.calculation;

import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.model.Transfer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationTransferTypeA implements ITaxCalculation {

    // Transactions executed at the same day has $3 plus 3% of the amount to be transferred
    @Override
    public BigDecimal calculate(Transfer transaction) {
        final float tax = 0.03F;
        final float fee = 3.00F;
        return transaction.getAmount().multiply(new BigDecimal(tax)).add(new BigDecimal(fee)).setScale(2, RoundingMode.HALF_UP);
    }

}
