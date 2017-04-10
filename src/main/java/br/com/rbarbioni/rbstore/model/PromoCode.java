package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by renan on 08/04/17.
 */
public class PromoCode implements Serializable {

    private static final long serialVersionUID = -5768922521579782272L;

    private final String name;

    private final String code;

    private final BigDecimal discount;

    @JsonCreator
    public PromoCode(@JsonProperty("name") String name, @JsonProperty("code") String code, @JsonProperty("discount") BigDecimal discount) {
        this.name = name;
        this.code = code;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
