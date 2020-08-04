package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.AccountDto;
import br.com.orion.scheduledtransfer.domain.exceptions.AccountNumberFormatException;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.orion.scheduledtransfer.infrastructure.utils.PageNumberUtils.adjustmentPagination;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/protected/accounts")
public class AccountResource {

    private IAccountService accountService;
    private IUserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<AccountDto> create(@Validated @RequestBody AccountDto accountDto, @AuthenticationPrincipal UserDetails principal){
        if (!accountDto.getNumber().matches("\\d{6}")){
            throw new AccountNumberFormatException("Account number must have 6 numbers");
        }
        User user = userService.findByUsername(principal.getUsername());
        Account account = accountDto.toAccount();
        account.setUser(user);
        account = accountService.create(account);
        accountDto = new AccountDto(account);

        return new ResponseEntity<AccountDto>(accountDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AccountDto>> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @AuthenticationPrincipal UserDetails principal){

        Long id = userService.findIdByUsername(principal.getUsername());

        pageNumber = adjustmentPagination(pageNumber);
        Page<Account> pageAccount = accountService.findAllByIdUser(id, pageNumber, pageSize);
        Page<AccountDto> resultDto = pageAccount.map(s -> s).map(AccountDto::new);

        return resultDto.getContent().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultDto);

    }

}
