package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by renan on 08/04/17.
 */
public class PreOrderRequest implements Serializable {

    private static final long serialVersionUID = -2481459967132385278L;

    private final Collection<Product> products;

    private final String code;

    private final Integer installmentCount;

    private final Customer customer;

    @JsonCreator
    public PreOrderRequest(
            @JsonProperty("items") Collection<Product> products,
            @JsonProperty("code") String code,
            @JsonProperty("installmentCount") Integer installmentCount,
            @JsonProperty("customer") Customer customer
            ) {
        this.products = products;
        this.code = code;
        this.installmentCount = installmentCount;
        this.customer = customer;
    }

    @Getter(name = "itens")
    public Collection<Product> getProducts() {
        return products;
    }

    public String getCode() {
        return code;
    }

    public Integer getInstallmentCount() {
        return installmentCount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getAmount(){
        BigDecimal amount = BigDecimal.ZERO;
        for (Product product : products) {
            amount = amount.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return amount;
    }
    public BigDecimal applyDiscount(BigDecimal amount , BigDecimal discount){
        return amount.multiply(discount);
    }

    public BigDecimal applyAdditional(BigDecimal amount , BigDecimal additional){
        return amount.multiply(additional);
    }
}