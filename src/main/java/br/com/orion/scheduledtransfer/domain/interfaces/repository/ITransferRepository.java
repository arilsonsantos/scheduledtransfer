package br.com.orion.scheduledtransfer.domain.interfaces.repository;

import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferRepository extends CrudRepository<Transfer, Long> {

    @Query("select t from Transfer t where t.accountFrom = :account or t.accountTo = :account")
    Page<Transfer> findAllTransferByAccount(@Param("account") Account account, Pageable pageable);

}
