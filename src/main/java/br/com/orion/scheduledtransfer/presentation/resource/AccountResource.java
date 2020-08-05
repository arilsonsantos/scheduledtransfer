package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.AccountDto;
import br.com.orion.scheduledtransfer.domain.exceptions.AccountNumberFormatException;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.User;
import br.com.orion.scheduledtransfer.application.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum.*;
import static br.com.orion.scheduledtransfer.infrastructure.utils.PageNumberUtils.adjustmentPagination;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/protected/accounts")
public class AccountResource {

    private IAccountService accountService;
    private IUserService userService;
    private MessageUtils messageUtils;

    @PostMapping
    @ResponseStatus(code = CREATED)
    public ResponseEntity<AccountDto> create(@Validated @RequestBody AccountDto accountDto, @AuthenticationPrincipal UserDetails principal){
        validateUser(accountDto);

        User user = userService.findByUsername(principal.getUsername());
        Account account = accountDto.toAccount();
        account.setUser(user);
        account = accountService.create(account);
        accountDto = new AccountDto(account);

        log.info(messageUtils.getMessage(ACCOUNT_SAVED));
        return new ResponseEntity<AccountDto>(accountDto, CREATED);
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

        return resultDto.getContent().isEmpty() ? noContent().build() : ok(resultDto);
    }

    private void validateUser(AccountDto accountDto){
        if (!accountDto.getNumber().matches("\\d{6}")){
            String warnMessage = messageUtils.getMessage(ACCOUNT_MUST_HAVE_SIX_NUMBERS);
            log.warn(warnMessage);
            throw new AccountNumberFormatException(warnMessage);
        }
    }

}
