package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by renan on 08/04/17.
 */
public class CheckoutRequest implements Serializable {

    private static final long serialVersionUID = -2481459967132385278L;

    private final Collection<Product> products;

    private final String cupom;

    private final Integer installmentCount;

    @JsonCreator
    public CheckoutRequest(
            @JsonProperty Collection<Product> products,
            @JsonProperty String cupom,
            @JsonProperty Integer installmentCount) {
        this.products = products;
        this.cupom = cupom;
        this.installmentCount = installmentCount;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public String getCupom() {
        return cupom;
    }

    public Integer getInstallmentCount() {
        return installmentCount;
    }

    public BigDecimal getAmmount(){
        BigDecimal ammount = BigDecimal.ZERO;
        for (Product product : products) {
            ammount = ammount.add(product.getPrice());
        }
        return ammount;
    }
    public BigDecimal applyDiscount(BigDecimal ammount , BigDecimal discount){
        return ammount.subtract(ammount.multiply(discount));
    }
}
