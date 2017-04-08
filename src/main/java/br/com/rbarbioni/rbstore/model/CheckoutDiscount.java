package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by renan on 08/04/17.
 */
public class CheckoutDiscount implements Serializable {

    private static final long serialVersionUID = -8589128874575784500L;

    private final BigDecimal ammount;

    private final BigDecimal discount;

    @JsonCreator
    public CheckoutDiscount(@JsonProperty BigDecimal ammount, @JsonProperty  BigDecimal discount) {
        this.ammount = ammount;
        this.discount = discount;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}