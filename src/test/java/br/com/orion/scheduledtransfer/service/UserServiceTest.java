package br.com.orion.scheduledtransfer.service;

import br.com.orion.scheduledtransfer.application.service.UserService;
import br.com.orion.scheduledtransfer.domain.interfaces.IRoleRepository;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Role;
import br.com.orion.scheduledtransfer.domain.model.User;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IUserRepository;
import br.com.orion.scheduledtransfer.application.utils.MessageUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    IUserService userService;

    @MockBean
    IUserRepository userRepository;

    @MockBean
    IRoleRepository roleRepository;

    @MockBean
    MessageUtils messageUtils;

    PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp(){
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userService = new UserService(userRepository, roleRepository, passwordEncoder, messageUtils);
    }

    @Test
    public void create_ok(){
        String pwd = passwordEncoder.encode("123");
        Role role = Role.builder().id(1L).name("USER").build();

        User user = User.builder()
                .id(1L)
                .username("johnn")
                .password("123").build();

        User userSaved = User.builder()
                .id(1L)
                .username("johnn")
                .roles(List.of(Role.builder().id(1L).name("USER").build()))
                .password(pwd).build();

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(userSaved);

        userSaved = userService.create(user);

        Assertions.assertThat(userSaved.getPassword()).isEqualTo(pwd);
        assertThat(userSaved.getRoles(), Matchers.hasSize(1));

    }

}
