package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by renan on 08/04/17.
 */
public class CheckoutDiscount implements Serializable {

    private static final long serialVersionUID = -8589128874575784500L;

    private final String ownId;

    private final BigDecimal amount;

    private final BigDecimal discount;

    @JsonCreator
    public CheckoutDiscount(@JsonProperty("amount") BigDecimal amount, @JsonProperty("discount") BigDecimal discount) {
        this.ownId = UUID.randomUUID().toString();
        this.amount = amount;
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public String getOwnId() {
        return ownId;
    }
}