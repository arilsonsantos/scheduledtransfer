package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.TransferNewDto;
import br.com.orion.scheduledtransfer.application.factory.TaxCalculationFactory;
import br.com.orion.scheduledtransfer.application.helper.TransferTypeEnumHelper;
import br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.interfaces.ITransferService;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import br.com.orion.scheduledtransfer.domain.model.User;
import br.com.orion.scheduledtransfer.infrastructure.config.JacksonConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@ActiveProfiles("test")
public class TransferResourceTest {

    private static final String TRANSFER_API = "/api/protected/transfers";
    private MockMvc mvc;

    @MockBean
    private IAccountService accountService;

    @MockBean
    private ITransferService transferService;

    @Autowired
    private WebApplicationContext webapp;

    @BeforeEach
    public void setUp() {
        mvc = webAppContextSetup(webapp).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    @DisplayName("Create a new transfer")
    public void execute_transfer_ok() throws Exception {
        var transferToAddDto = TransferNewDto.builder()
                .accountFrom("000001")
                .accountTo("000002")
                .amount(new BigDecimal("100"))
                .dateSchedule(now().plusDays(11))
                .build();

        int days = (int) DAYS.between(LocalDate.now(), transferToAddDto.getDateSchedule());
        TransferTypeEnum typeEnum = TransferTypeEnumHelper.valueOf(days);

        Account accountFrom = Account.builder()
                .id(1L).number(transferToAddDto.getAccountFrom())
                .user(User.builder().id(1L)
                .username("user").build())
                .build();

        Account accountTo = Account.builder()
                .id(1L).number(transferToAddDto.getAccountTo())
                .user(User.builder().id(1L)
                        .username("user").build())
                .build();

        Mockito.when(accountService.getByAccountNumber(anyString())).thenReturn(accountFrom);

        var transferSaved = Transfer.builder().id(1L)
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .dateRegistration(LocalDate.now())
                .dateSchedule(transferToAddDto.getDateSchedule())
                .amount(transferToAddDto.getAmount())
               // .transferTypeEnum(typeEnum)
                .build();

        ITaxCalculation taxCalculation = TaxCalculationFactory.getTaxCalculationTransfer(typeEnum);
        BigDecimal tax = taxCalculation.calculate(transferSaved);
        transferSaved.setTax(tax);

        when(transferService.execute(Mockito.any(Transfer.class))).thenReturn(transferSaved);

        transferService.execute(transferSaved);

        String requestJson = new JacksonConfig().objectMapper().writeValueAsString(transferToAddDto);
        var request = MockMvcRequestBuilders.post(TRANSFER_API).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson);


        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());

    }


}
