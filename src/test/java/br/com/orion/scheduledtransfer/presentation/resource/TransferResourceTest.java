package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.TransferNewDto;
import br.com.orion.scheduledtransfer.application.factory.TaxCalculationFactory;
import br.com.orion.scheduledtransfer.application.helper.TransferTypeEnumHelper;
import br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.interfaces.ITransferService;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IUserRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@ActiveProfiles("test")
public class TransferResourceTest {

    private static final String TRANSFER_API = "/api/protected/transfers";
    private MockMvc mvc;

    @MockBean
    private IUserRepository userRepository;

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

    @Test
    @WithMockUser
    @DisplayName("Get all transfer by account")
    public void get_all_transfer() throws Exception {
        String accountNumber = "000001";
        when(userRepository.getIdByUsername("user")).thenReturn(Optional.of(1l));

        Page<Transfer> pageTransfer = getTransfers();
        when(transferService.findAllTransferByAccountNumberAndIdUser(anyString(), anyLong(), anyInt(), anyInt())).thenReturn(pageTransfer);

        var request = MockMvcRequestBuilders.get(TRANSFER_API.concat("/").concat(accountNumber)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(3)));
    }

    private Page<Transfer> getTransfers() {
        User user = User.builder().id(1L).username("user").build();
        Account c1 = Account.builder().id(1L).number("000001").user(user).build();
        Account c2 = Account.builder().id(2L).number("000002").user(user).build();
        Account c3 = Account.builder().id(3L).number("000003").user(user).build();
        var t1 = Transfer.builder().id(1L).accountFrom(c1).accountTo(c2).amount(new BigDecimal("50")).dateRegistration(now()).dateSchedule(now().plusDays(15)).build();
        var t2 = Transfer.builder().id(2L).accountFrom(c1).accountTo(c2).amount(new BigDecimal("50")).dateRegistration(now()).dateSchedule(now().plusDays(15)).build();
        var t3 = Transfer.builder().id(3L).accountFrom(c3).accountTo(c1).amount(new BigDecimal("50")).dateRegistration(now()).dateSchedule(now().plusDays(15)).build();

        PageRequest pageable = PageRequest.of(0, 1);
        PageImpl<Transfer> page = new PageImpl<Transfer>(Arrays.asList(t1, t2, t3), pageable, 2);

        return page;
    }

}
