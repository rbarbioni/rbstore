package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by renan on 08/04/17.
 */
public class FundingInstrument implements Serializable{

    private static final long serialVersionUID = 6900868261377995719L;

    private final PaymentType method;

    private final CreditCard creditCard;

    @JsonCreator
    public FundingInstrument(
            @JsonProperty PaymentType method,
            @JsonProperty CreditCard creditCard) {
        this.method = method;
        this.creditCard = creditCard;
    }

    public PaymentType getMethod() {
        return method;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}