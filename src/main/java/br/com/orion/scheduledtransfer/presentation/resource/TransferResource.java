package br.com.orion.scheduledtransfer.presentation.resource;

import br.com.orion.scheduledtransfer.application.dto.TransferDto;
import br.com.orion.scheduledtransfer.application.dto.TransferNewDto;
import br.com.orion.scheduledtransfer.domain.exceptions.AccountNotFoundException;
import br.com.orion.scheduledtransfer.domain.exceptions.InvalidDateException;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.ITransferService;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static br.com.orion.scheduledtransfer.infrastructure.utils.PageNumberUtils.adjustmentPagination;

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

        validateUsername(accountFrom.getUser().getUsername(), user.getUsername());
        validateScheduleDate(transferNewDto.getDateSchedule());

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


    @GetMapping("{accountNumber}")
    public ResponseEntity<Page<TransferDto>> findAllByAccountNumber(@PathVariable("accountNumber") String accountNumber,
                                                                    @RequestParam(defaultValue = "0") Integer pageNumber,
                                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                                    @AuthenticationPrincipal UserDetails user) {

        Long idUser = userService.findIdByUsername(user.getUsername());

        pageNumber = adjustmentPagination(pageNumber);
        Page<Transfer> transfers = transferService.findAllTransferByAccountAndUser(accountNumber, idUser, pageNumber, pageSize);

        Page<TransferDto> transfersDto =  transfers.map(t -> t).map(TransferDto::new);

        if (transfers.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(transfersDto);
    }

    private void validateUsername(String userRequest, String username){
        if (!username.equalsIgnoreCase(userRequest)){
            throw  new AccountNotFoundException("Account not exist or belongs to another user");
        }
    }

    private void validateScheduleDate(LocalDate date){
        if (date.isBefore(LocalDate.now())){
            throw new InvalidDateException("Scheduled date can't be less than today");
        }
    }

}
