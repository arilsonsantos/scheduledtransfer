package br.com.orion.scheduledtransfer.application.service;

import br.com.orion.scheduledtransfer.application.factory.TaxCalculationFactory;
import br.com.orion.scheduledtransfer.application.helper.TransferTypeEnumHelper;
import br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum;
import br.com.orion.scheduledtransfer.domain.exceptions.AccountNotFoundException;
import br.com.orion.scheduledtransfer.domain.exceptions.TaxNotAvailableException;
import br.com.orion.scheduledtransfer.domain.interfaces.IAccountService;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;
import br.com.orion.scheduledtransfer.domain.interfaces.ITransferService;
import br.com.orion.scheduledtransfer.domain.interfaces.repository.ITransferRepository;
import br.com.orion.scheduledtransfer.domain.model.Account;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import br.com.orion.scheduledtransfer.application.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum.*;
import static br.com.orion.scheduledtransfer.infrastructure.utils.DateUtils.daysBetween;

@Slf4j
@Service
@AllArgsConstructor
public class TransferService implements ITransferService {

    private ITransferRepository transferRepository;
    private IAccountService accountService;
    private MessageUtils messageUtils;

    @Override
    public Transfer execute(Transfer transfer) {
        int days = daysBetween(transfer.getDateRegistration(), transfer.getDateSchedule());
        TransferTypeEnum typeEnum = TransferTypeEnumHelper.valueOf(days);

        ITaxCalculation taxCalculation = TaxCalculationFactory.getTaxCalculationTransfer(typeEnum);
        BigDecimal tax = taxCalculation.calculate(transfer);

        validateTaxCalculation(tax);
        transfer.setTax(tax);

        transfer = transferRepository.save(transfer);

        log.info(messageUtils.getMessage(TRANSFER_EXECUTED_SUCCESS, transfer.getAccountFrom().getNumber() + " -> " + transfer.getAccountTo().getNumber()));

        return transfer;
    }

    @Override
    public Page<Transfer> findAllTransferByAccountNumberAndIdUser(String accountNumber, Long idUser, Integer pageNumber, Integer pageSize) {
        Account account = accountService.getByAccountNumber(accountNumber);

        if (!account.getUser().getId().equals(idUser)){
            String error = messageUtils.getMessage(ACCOUNT_NOT_EXIST);
            log.error(error);
            throw new AccountNotFoundException(error);
        }
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        var transfers = transferRepository.findAllTransferByAccount(account, paging);

        return transfers;
    }


    private void validateTaxCalculation(BigDecimal taxCalculation){
        if (taxCalculation == null){
            String error = messageUtils.getMessage(CONDITION_NOT_ACCEPTABLE);
            log.error(error);
            throw new TaxNotAvailableException(error);
        }
    }

}