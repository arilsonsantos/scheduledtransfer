package br.com.orion.scheduledtransfer.service.calculate;

import br.com.orion.scheduledtransfer.application.service.calculation.CalculationTransferTypeB;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculationTransferTypeBTest {

    @Test
    @DisplayName("$12 multiplied by number of days from the scheduling date to transfer date")
    public void calculateB_test(){
        Transfer transfer = transfer_with_calculate_condition_B();
        ITaxCalculation calc = new CalculationTransferTypeB();
        transfer.setTax(calc.calculate(transfer));

        Assertions.assertThat(transfer.getTax()).isEqualTo(new BigDecimal("60.00"));
    }

    private Transfer transfer_with_calculate_condition_B(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100));
        transfer.setDateRegistration(LocalDate.now().minusDays(5));
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }

}
