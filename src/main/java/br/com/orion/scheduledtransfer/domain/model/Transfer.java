package br.com.orion.scheduledtransfer.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT_FROM")
    private Account accountFrom;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT_TO")
    private Account accountTo;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal tax;

    @NotNull
    private LocalDate dateRegistration;

    @NotNull
    private LocalDate dateSchedule;

}