package br.com.orion.scheduledtransfer.domain.interfaces;

import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import br.com.orion.scheduledtransfer.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ITransferService {

    Transfer execute(Transfer transfer);

    Page<Transfer> findAllTransferByAccountAndUser(String accountNumber, Long idUer, Integer pageNumber, Integer pageSize);

}
