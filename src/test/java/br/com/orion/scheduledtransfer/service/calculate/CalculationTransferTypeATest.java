package br.com.orion.scheduledtransfer.service.calculate;

import br.com.orion.scheduledtransfer.application.service.calculation.CalculationTransferTypeA;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculationTransferTypeATest {

    @Test
    @DisplayName("$3 plus 3% of the amount to be transferred")
    public void calculateA_test(){
        Transfer transfer = transfer_with_calculate_condition_A();
        ITaxCalculation calc = new CalculationTransferTypeA();
        transfer.setTax(calc.calculate(transfer));

        Assertions.assertThat(transfer.getTax()).isEqualTo(new BigDecimal("6.00"));
    }

    private Transfer transfer_with_calculate_condition_A(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100));
        transfer.setDateRegistration(LocalDate.now());
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }
}
