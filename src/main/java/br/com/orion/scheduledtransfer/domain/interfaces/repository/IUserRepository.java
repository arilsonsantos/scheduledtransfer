package br.com.orion.scheduledtransfer.domain.interfaces.repository;

import br.com.orion.scheduledtransfer.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

    Optional<User> getByUsername(String username);

    @Query("select count(u) from User u where u.username = :username")
    Integer isExists(@Param("username") String username);

    @Query("select id from User where username = :username")
    Optional<Long> getIdByUsername(@Param(("username")) String username);
}
