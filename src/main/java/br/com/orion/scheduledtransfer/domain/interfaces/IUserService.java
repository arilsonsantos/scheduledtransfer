package br.com.orion.scheduledtransfer.domain.interfaces;

import br.com.orion.scheduledtransfer.domain.model.User;

public interface IUserService {

    User findByUsername(String username);

    Long findIdByUsername(String username);

    User create(User user);

}
