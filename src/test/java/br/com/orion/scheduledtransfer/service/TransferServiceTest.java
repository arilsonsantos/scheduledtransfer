package br.com.orion.scheduledtransfer.service;

import br.com.orion.scheduledtransfer.application.service.TransferService;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.ITransferService;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.ITransferRepository;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransferServiceTest {

    ITransferService transferService;
    IAccountService accountService;

    @MockBean
    ITransferRepository repository;

    @BeforeEach
    public void setUp(){
        transferService = new TransferService(repository, accountService);
    }

    @Test
    @DisplayName("$3 plus 3% of the amount to be transferred")
    public void calculateA_test(){
        Transfer transfer = transfer_with_calculate_condition_B();

        var transferSaved = Transfer.builder().id(1L)
                .accountFrom(transfer.getAccountFrom())
                .accountTo(transfer.getAccountTo())
                .dateRegistration(transfer.getDateRegistration())
                .dateSchedule(transfer.getDateSchedule())
                .amount(transfer.getAmount())
                .build();

        when(repository.save(Mockito.any(Transfer.class))).thenReturn(transferSaved);

        transferService.execute(transfer);

        Assertions.assertThat(transfer.getTax()).isEqualTo(new BigDecimal("60.00"));
    }

    private Transfer transfer_with_calculate_condition_B(){
        Account from = Account.builder().id(1L).number("123").build();
        Account to = Account.builder().id(2L).number("1234").build();
        var transfer = Transfer.builder()
                .accountFrom(from)
                .accountTo(to)
                .amount(new BigDecimal("100"))
                .dateRegistration(LocalDate.now())
                .dateSchedule(LocalDate.now().plusDays(5))
                .build();

        return transfer;
    }
}
