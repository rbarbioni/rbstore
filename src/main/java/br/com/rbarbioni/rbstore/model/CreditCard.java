package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by renan on 08/04/17.
 */
public class CreditCard implements Serializable{

    private static final long serialVersionUID = -3499451909383399757L;

    private final Integer expirationMonth;

    private final Integer expirationYear;

    private final String number;

    private final String cvc;

    private final Customer holder;

    @JsonCreator
    public CreditCard(
            @JsonProperty Integer expirationMonth,
            @JsonProperty Integer expirationYear,
            @JsonProperty String number,
            @JsonProperty String cvc,
            @JsonProperty Customer holder) {
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.number = number;
        this.cvc = cvc;
        this.holder = holder;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public String getNumber() {
        return number;
    }

    public String getCvc() {
        return cvc;
    }

    public Customer getHolder() {
        return holder;
    }
}