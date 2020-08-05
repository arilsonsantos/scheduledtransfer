package br.com.orion.scheduledtransfer.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of= "number")
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