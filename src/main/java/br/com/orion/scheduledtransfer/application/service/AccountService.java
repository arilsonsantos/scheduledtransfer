package br.com.orion.scheduledtransfer.application.service;

import br.com.orion.scheduledtransfer.domain.exceptions.AccountNotFoundException;
import br.com.orion.scheduledtransfer.domain.exceptions.ResourceAlreadyExistsException;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IAccountRepository;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.application.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum.ACCOUNT_ALREADY_EXISTS;
import static br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum.ACCOUNT_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private IAccountRepository repository;
    private MessageUtils messageUtils;

    public Account create(Account account) {
        isExist(account.getNumber());
        account = repository.save(account);
        return account;
    }

    @Override
    public Page<Account> findAllByIdUser(Long idUser, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        return repository.findAllByIdUser(idUser, paging);
    }

    @Override
    public boolean isExist(String accountNumber) {
        Optional<Account> account = repository.findByNumberEquals(accountNumber);
        if (account.isPresent()) {
            String msgError = messageUtils.getMessage(ACCOUNT_ALREADY_EXISTS, accountNumber);
            log.error(msgError);
            throw new ResourceAlreadyExistsException(msgError);
        }
        return false;
    }

    @Override
    public Account getByAccountNumber(String accountNumber) {
        String msgError = messageUtils.getMessage(ACCOUNT_NOT_FOUND, accountNumber);
        log.error(msgError);
       return repository.findByNumberEquals(accountNumber).orElseThrow(() -> new AccountNotFoundException(msgError));
    }

}