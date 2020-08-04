package br.com.orion.scheduledtransfer.domain.interfaces.repository;

import br.com.orion.scheduledtransfer.domain.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends CrudRepository<Account, Long>{

    List<Account> findAll();

    Optional<Account> findByNumberEquals(String number);

    @Query("select a from Account a where a.user.id = :idUser")
    Page<Account> findAllByIdUser(@Param("idUser") Long id, Pageable pageable);
    
}