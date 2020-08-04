package br.com.orion.scheduledtransfer.presentation;

import br.com.orion.scheduledtransfer.application.dto.TransferDto;
import br.com.orion.scheduledtransfer.application.dto.TransferNewDto;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.ITransferService;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/protected/transfers")
@AllArgsConstructor
@Slf4j
public class TransferResource {

    private ITransferService transferService;
    private IAccountService accountService;
    private IUserService userService;

    @PostMapping
    public ResponseEntity<TransferDto> execute(@Validated @RequestBody TransferNewDto transferNewDto, @AuthenticationPrincipal UserDetails user){
        Account accountFrom = accountService.getByAccountNumber(transferNewDto.getAccountFrom());
        Account accountTo = accountService.getByAccountNumber(transferNewDto.getAccountTo());

        Transfer transfer = Transfer.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .dateRegistration(LocalDate.now())
                .dateSchedule(transferNewDto.getDateSchedule())
                .amount(transferNewDto.getAmount())
                .build();

        transfer = transferService.execute(transfer);

        TransferDto transferDto = new TransferDto(transfer);

        return new ResponseEntity<TransferDto>(transferDto, HttpStatus.CREATED);
    }

}
