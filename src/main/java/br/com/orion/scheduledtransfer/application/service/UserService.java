package br.com.orion.scheduledtransfer.application.service;

import br.com.orion.scheduledtransfer.domain.exceptions.ResourceAlreadyExistsException;
import br.com.orion.scheduledtransfer.domain.exceptions.ResourceNotFoundException;
import br.com.orion.scheduledtransfer.domain.interfaces.IRoleRepository;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Role;
import br.com.orion.scheduledtransfer.domain.model.User;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public User create(User user){
        if (userRepository.getByUsername(user.getUsername()).isPresent()){
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        Role role = roleRepository.findById(1L).get();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnable(true);
        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.getByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found."));
    }

    public Long findIdByUsername(String username){
        return userRepository.getIdByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found."));
    }


}
