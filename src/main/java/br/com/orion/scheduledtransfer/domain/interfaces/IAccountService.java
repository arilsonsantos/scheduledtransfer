package br.com.orion.scheduledtransfer.domain.interfaces;

import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

    Account create(Account account);

    Page<Account> findAllByIdUser(Long idUser, Integer pageNumber, Integer pageSize);

    boolean isExist(String number);

    Account getByAccountNumber(String accountNumber);
    
}