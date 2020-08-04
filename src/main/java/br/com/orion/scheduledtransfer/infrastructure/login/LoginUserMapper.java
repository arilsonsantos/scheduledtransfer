package br.com.orion.scheduledtransfer.infrastructure.login;

import br.com.orion.scheduledtransfer.domain.model.Role;
import br.com.orion.scheduledtransfer.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoginUserMapper {

    public LoginUser map(User user){
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(Role::getName)
                .map(s -> "ROLE_".concat(s))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return LoginUser.builder().username(user.getUsername())
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }

}
