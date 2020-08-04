package br.com.orion.scheduledtransfer.application.dto;

import br.com.orion.scheduledtransfer.application.helper.TransferTypeEnumHelper;
import br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum;
import br.com.orion.scheduledtransfer.domain.model.Transfer;
import br.com.orion.scheduledtransfer.infrastructure.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.orion.scheduledtransfer.infrastructure.utils.DateUtils.daysBetween;
import static java.time.temporal.ChronoUnit.DAYS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferDto implements Serializable {

    private Long id;

    private String accountFrom;

    private String accountTo;

    private BigDecimal amount;

    private BigDecimal tax;

    private LocalDate dateRegistration;

    private LocalDate dateSchedule;

    @JsonProperty("taxType")
    private String transferTypeEnum;



    public TransferDto (Transfer transfer){
        int days = daysBetween(transfer.getDateRegistration(), transfer.getDateSchedule());
        TransferTypeEnum typeEnum = TransferTypeEnumHelper.valueOf(days);

        id = transfer.getId();
        accountFrom = transfer.getAccountFrom().getNumber();
        accountTo = transfer.getAccountTo().getNumber();
        amount = transfer.getAmount();
        tax = transfer.getTax();
        dateRegistration = transfer.getDateRegistration();
        dateSchedule = transfer.getDateSchedule();
        transferTypeEnum = typeEnum.getType();
    }

}
