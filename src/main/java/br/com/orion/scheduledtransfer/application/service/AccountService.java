package br.com.orion.scheduledtransfer.application.service;

import br.com.orion.scheduledtransfer.domain.exceptions.ResourceAlreadyExistsException;
import br.com.orion.scheduledtransfer.domain.exceptions.ResourceNotFoundException;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.IAccountRepository;
import br.com.orion.scheduledtransfer.domain.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private IAccountRepository repository;

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
    public boolean isExist(String number) {
        Optional<Account> account = repository.findByNumberEquals(number);
        if (account.isPresent()) {
            throw new ResourceAlreadyExistsException("There is already an account with this number");
        }
        return false;
    }

    @Override
    public Account getByAccountNumber(String accountNumber) {
       return repository.findByNumberEquals(accountNumber).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

}