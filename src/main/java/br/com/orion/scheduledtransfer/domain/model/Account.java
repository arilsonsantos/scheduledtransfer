package br.com.orion.scheduledtransfer.domain.model;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of= "number")
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @ManyToMany
    @JoinColumns({@JoinColumn(name = "ID_ACCOUNT_FROM", referencedColumnName = "id"),
             @JoinColumn(name = "ID_ACCOUNT_TO", referencedColumnName = "id")})
    private List<Transfer> transactions;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}