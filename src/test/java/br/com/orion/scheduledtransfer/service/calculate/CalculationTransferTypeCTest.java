package br.com.orion.scheduledtransfer.service.calculate;

import br.com.orion.scheduledtransfer.application.service.calculation.CalculationTransferTypeC;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculationTransferTypeCTest {

    @Test
    @DisplayName("8% of the amount more than 10 days until 20 days from scheduling")
    public void calculate_11_20(){
        Transfer transaction = transfer_with_calculate_condition_C_with_more_than_10_days_and_lessEquals_than_20_days();
        ITaxCalculation calc = new CalculationTransferTypeC();
        transaction.setTax(calc.calculate(transaction));

        Assertions.assertThat(transaction.getTax()).isEqualTo(new BigDecimal("8.00"));
    }

    @Test
    @DisplayName("6% of the amount more than 20 days until 30 days from scheduling")
    public void calculate_21_30(){
        Transfer transaction = transfer_with_calculate_condition_C_with_more_than_20_and_lessEquals_than_30();
        ITaxCalculation calc = new CalculationTransferTypeC();
        transaction.setTax(calc.calculate(transaction));

        Assertions.assertThat(transaction.getTax()).isEqualTo(new BigDecimal("6.00"));
    }

    @Test
    @DisplayName("4% of the amount more than 30 days until 40 days from scheduling")
    public void calculate_31_40(){
        Transfer transaction = transfer_with_calculate_condition_C_with_more_than_30_and_lessEquals_than_40();
        ITaxCalculation calc = new CalculationTransferTypeC();
        transaction.setTax(calc.calculate(transaction));

        Assertions.assertThat(transaction.getTax()).isEqualTo(new BigDecimal("4.00"));
    }

    @Test
    @DisplayName("2% of the amount more than 40 days from scheduling and amount more than 100_000")
    public void calculateC_41_over_and_amount_bigger_than_100_000(){
        Transfer transfer = transfer_with_calculate_condition_C_with_calculate_C_41_over_and_amount_bigger_than_100_000();
        ITaxCalculation calc = new CalculationTransferTypeC();
        transfer.setTax(calc.calculate(transfer));

        Assertions.assertThat(transfer.getTax()).isEqualTo(new BigDecimal("2000.02"));
    }

    @Test
    @DisplayName("Must return NULL when more than 40 days from scheduling and amount less or equals 100_000" )
    public void must_return_null_when_calculateC_41_over_and_amount_less_or_equals_than_100_000(){
        Transfer transfer = transfer_with_calculate_condition__C_41_over_and_amount_less_or_equals_than_100_000();
        ITaxCalculation calc = new CalculationTransferTypeC();
        transfer.setTax(calc.calculate(transfer));
        Assertions.assertThat(transfer.getTax()).isNull();
    }

    private Transfer transfer_with_calculate_condition_C_with_more_than_10_days_and_lessEquals_than_20_days(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100));
        transfer.setDateRegistration(LocalDate.now().minusDays(11));
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }

    private Transfer transfer_with_calculate_condition_C_with_more_than_20_and_lessEquals_than_30(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100));
        transfer.setDateRegistration(LocalDate.now().minusDays(30));
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }

    private Transfer transfer_with_calculate_condition_C_with_more_than_30_and_lessEquals_than_40(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100));
        transfer.setDateRegistration(LocalDate.now().minusDays(38));
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }


    private Transfer transfer_with_calculate_condition_C_with_calculate_C_41_over_and_amount_bigger_than_100_000(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100_001));
        transfer.setDateRegistration(LocalDate.now().minusDays(41));
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }

    private Transfer transfer_with_calculate_condition__C_41_over_and_amount_less_or_equals_than_100_000(){
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(100));
        transfer.setDateRegistration(LocalDate.now().minusDays(41));
        transfer.setDateSchedule(LocalDate.now());

        return transfer;
    }

}
