package br.com.orion.scheduledtransfer.service;

import br.com.orion.scheduledtransfer.application.service.AccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IAccountRepository;
import br.com.orion.scheduledtransfer.domain.model.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    IAccountService accountService;

    @MockBean
    IAccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void create_account() {
        Account account = Account.builder().id(1L).number("123456").build();
        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        accountService.create(account);

        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat((account.getId())).isEqualTo(1L);
    }

}