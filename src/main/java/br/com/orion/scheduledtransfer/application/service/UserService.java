package br.com.orion.scheduledtransfer.application.service;

import br.com.orion.scheduledtransfer.domain.exceptions.ResourceAlreadyExistsException;
import br.com.orion.scheduledtransfer.domain.exceptions.UserNotFoundException;
import br.com.orion.scheduledtransfer.domain.interfaces.IRoleRepository;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Role;
import br.com.orion.scheduledtransfer.domain.model.User;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IUserRepository;
import br.com.orion.scheduledtransfer.application.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private MessageUtils messageUtils;

    public User create(User user){
        if (userRepository.getByUsername(user.getUsername()).isPresent()){
            String msgError = messageUtils.getMessage(USER_ALREADY_EXISTS);
            log.warn(msgError);
            throw new ResourceAlreadyExistsException(msgError);
        }

        Role role = roleRepository.findById(1L).get();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnable(true);
        user.setRoles(Arrays.asList(role));


        user = userRepository.save(user);
        log.info(messageUtils.getMessage(USER_HAS_SAVED, user.getUsername()));
        return user;
    }

    public User findByUsername(String username){
        return userRepository.getByUsername(username).orElseThrow(()-> new UserNotFoundException(messageUtils.getMessage(USER_NOT_FOUND)));
    }

    public Long findIdByUsername(String username){
        return userRepository.getIdByUsername(username).orElseThrow(()-> new UserNotFoundException(messageUtils.getMessage(USER_NOT_FOUND)));
    }


}
