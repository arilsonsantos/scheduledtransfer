package br.com.orion.scheduledtransfer.application.dto;

import br.com.orion.scheduledtransfer.domain.model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String number;

   public AccountDto(Account account){
       id = account.getId();
       number = account.getNumber();
   }

   public Account toAccount(){
       return Account.builder().id(id).number(number).build();
   }

}
