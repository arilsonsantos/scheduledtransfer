package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


//@WebMvcTest(controllers = AccountResource.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserResourceTest {

    private static final String PUBLIC_USER_API = "/api/public/users";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webapp;

    @BeforeEach
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(webapp).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("Create a new user")
    public void create_user_ok() throws Exception{
        UserDto user = UserDto.builder().username("johnn").password("123").build();

        String json = new ObjectMapper().writeValueAsString(user);

        var request = MockMvcRequestBuilders.post(PUBLIC_USER_API).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
