package br.com.orion.scheduledtransfer.domain.interfaces;

import br.com.orion.scheduledtransfer.domain.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<Role, Long> {
}
