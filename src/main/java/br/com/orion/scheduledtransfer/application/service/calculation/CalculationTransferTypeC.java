package br.com.orion.scheduledtransfer.application.service.calculation;

import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import br.com.orion.scheduledtransfer.infrastructure.utils.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculationTransferTypeC implements ITaxCalculation {

    @Override
    public BigDecimal calculate(Transfer transaction) {
        final float tax1020 = 0.08F;
        final float tax2030 = 0.06F;
        final float tax3040 = 0.04F;
        final float tax40Over = 0.02F;

        var multiplier = (int) DAYS.between(transaction.getDateRegistration(), transaction.getDateSchedule());

        BigDecimal totalTax = BigDecimal.ZERO;

        if (NumberUtils.isNumberIntoRange(multiplier, 10, 20)) {
            totalTax = transaction.getAmount().multiply(new BigDecimal(tax1020)).setScale(2, RoundingMode.HALF_UP);
            return totalTax;
        } else if (NumberUtils.isNumberIntoRange(multiplier, 20, 30)) {
            totalTax = transaction.getAmount().multiply(new BigDecimal(tax2030)).setScale(2, RoundingMode.HALF_UP);
            return totalTax;
        } else if (NumberUtils.isNumberIntoRange(multiplier, 30, 40)) {
            totalTax = transaction.getAmount().multiply(new BigDecimal(tax3040)).setScale(2, RoundingMode.HALF_UP);
            return totalTax;
        } else if (multiplier > 40 && transaction.getAmount().compareTo(new BigDecimal(100_000)) == 1) {
            totalTax = transaction.getAmount().multiply(new BigDecimal(tax40Over)).setScale(2, RoundingMode.HALF_UP);
            return totalTax;
        }

        return  null;

    }

}

