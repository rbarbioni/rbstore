package br.com.rbarbioni.bluebank.model.dto;

import br.com.rbarbioni.bluebank.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by renan on 10/02/2017.
 */
public class AccountTransferDto {

    @NotNull
    @JsonProperty("source")
    private Account source;

    @NotNull
    @JsonProperty("destination")
    private Account destination;

    @NotNull
    @JsonProperty("amount")
    Double amount;

    private AccountTransferDto() {
        super();
    }

    public AccountTransferDto(Account source, Account destination, Double amount) {
        this();
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public Account getSource() {
        return source;
    }

    public Account getDestination() {
        return destination;
    }

    public Double getAmount() {
        return amount;
    }
}
