package br.com.rbarbioni.rbstore.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by renan on 10/04/17.
 */
public class Subtotals implements Serializable {

    private static final long serialVersionUID = 6606159140401273821L;

    private final BigDecimal addition;

    private final BigDecimal discount;

    public Subtotals(BigDecimal addition, BigDecimal discount) {
        this.addition = addition;
        this.discount = discount;
    }

    public BigDecimal getAddition() {
        return addition;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
