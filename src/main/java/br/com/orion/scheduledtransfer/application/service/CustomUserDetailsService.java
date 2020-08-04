package br.com.orion.scheduledtransfer.application.service;

import br.com.orion.scheduledtransfer.domain.exceptions.ResourceNotFoundException;
import br.com.orion.scheduledtransfer.infrastructure.login.LoginUser;
import br.com.orion.scheduledtransfer.infrastructure.login.LoginUserMapper;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private IUserRepository repository;
    private LoginUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = repository.getByUsername(username)
                .map(userMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user;
    }
}
