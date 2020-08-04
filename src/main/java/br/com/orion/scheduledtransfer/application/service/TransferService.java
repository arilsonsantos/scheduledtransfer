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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.orion.scheduledtransfer.infrastructure.utils.DateUtils.daysBetween;

@Service
@AllArgsConstructor
public class TransferService implements ITransferService {

    private ITransferRepository transferRepository;
    private IAccountService accountService;

    @Override
    public Transfer execute(Transfer transfer) {
        int days = daysBetween(transfer.getDateRegistration(), transfer.getDateSchedule());
        TransferTypeEnum typeEnum = TransferTypeEnumHelper.valueOf(days);

        ITaxCalculation taxCalculation = TaxCalculationFactory.getTaxCalculationTransfer(typeEnum);
        BigDecimal tax = taxCalculation.calculate(transfer);

        validateTaxCalculation(tax);
        transfer.setTax(tax);

        transfer = transferRepository.save(transfer);
       // transfer.setTransferTypeEnum(typeEnum);

        return transfer;
    }

    @Override
    public Page<Transfer> findAllTransferByAccountAndUser(String accountNumber, Long idUser, Integer pageNumber, Integer pageSize) {
        Account account = accountService.getByAccountNumber(accountNumber);

        if (!account.getUser().getId().equals(idUser)){
            throw new AccountNotFoundException("Account not exits or belongs to another user.");
        }
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        var transfers = transferRepository.findAllTransferByAccount(account, paging);

        return transfers;
    }


    private void validateTaxCalculation(BigDecimal taxCalculation){
        if (taxCalculation == null){
            throw new TaxNotAvailableException("Condition not acceptable, please review the amount and/or scheduling date.");
        }
    }

}