package br.com.orion.scheduledtransfer.application.service.calculation;

import br.com.orion.scheduledtransfer.domain.model.Transfer;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;

import java.math.BigDecimal;
import java.time.Period;

public class CalculationTransferTypeB implements ITaxCalculation {

    // Transactions executed by 10 days from schedule date has $12 multiplied by number of days
    // from the scheduling date to transfer date.
    @Override
    public BigDecimal calculate(Transfer transaction) {
        final int tax = 12;
        final int multiplier = Period.between(transaction.getDateRegistration(), transaction.getDateSchedule()).getDays();
        final BigDecimal totalTax = new BigDecimal(tax * multiplier).setScale(2);

        return totalTax;
    }

}
