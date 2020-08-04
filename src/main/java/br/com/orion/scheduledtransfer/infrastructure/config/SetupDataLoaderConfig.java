package br.com.orion.scheduledtransfer.infrastructure.config;

import br.com.orion.scheduledtransfer.domain.interfaces.IRoleRepository;
import br.com.orion.scheduledtransfer.domain.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class SetupDataLoaderConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final IRoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roleRepository.deleteAll();

        Role user = Role.builder().name("USER").build();
        createRoleIfNotFound(user);

    }

    private void createRoleIfNotFound(Role role) {
        Optional<Role> obj = roleRepository.findById(1L);
        if (obj.isPresent()) {
            return;
        }
        roleRepository.save(role);
    }

}
