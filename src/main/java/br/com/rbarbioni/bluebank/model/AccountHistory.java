package br.com.rbarbioni.bluebank.model;

import br.com.rbarbioni.bluebank.model.enums.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by renan on 13/02/17.
 */
@Entity
@Table(name = "Account_History")
public class AccountHistory extends AbstractAccount{

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    private Operation operation;

    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;

    public AccountHistory(){}
    public AccountHistory(Account account, Operation operation, BigDecimal valor) {
        super(account.getCpf(), account.getAgencia(), account.getNumero());
        this.operation = operation;
        this.valor = valor;
    }

    public Operation getOperation() {
        return operation;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
