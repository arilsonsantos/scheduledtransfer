package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.AccountDto;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.User;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IAccountRepository;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest
@ActiveProfiles("test")
public class AccountResourceTest {

    private static final String ACCOUNT_API = "/api/protected/accounts";
    private MockMvc mvc;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IAccountRepository accountRepository;


    @Autowired
    private WebApplicationContext webapp;

    @BeforeEach
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(webapp).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    @DisplayName("Create a new count")
    public void create_account_ok() throws Exception{
        User user = User.builder().id(1L).username("user").build();

        AccountDto accountDto = AccountDto.builder().id(1L).number("123456").build();
        Account accountToSave = Account.builder().id(1L).number("123456").build();
        Account account = Account.builder().id(1L).number("123456").user(user).build();

        when(userRepository.getByUsername(anyString())).thenReturn(Optional.of(User.builder().id(1L).username("user").build()));
        when(accountRepository.save(accountToSave)).thenReturn(account);

        String requestJson = new ObjectMapper().writeValueAsString(accountDto);

        var request = MockMvcRequestBuilders.post(ACCOUNT_API).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
