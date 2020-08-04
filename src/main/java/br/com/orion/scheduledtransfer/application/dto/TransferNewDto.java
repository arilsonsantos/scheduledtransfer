package br.com.orion.scheduledtransfer.application.dto;

import br.com.orion.scheduledtransfer.domain.model.Transfer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferNewDto implements Serializable {

    @NotEmpty
    private String accountFrom;

    @NotEmpty
    private String accountTo;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate dateSchedule;

    public  TransferNewDto (Transfer transfer){
        accountFrom = transfer.getAccountFrom().getNumber();
        accountTo = transfer.getAccountTo().getNumber();
        amount = transfer.getAmount();
        dateSchedule = transfer.getDateSchedule();
    }


}
