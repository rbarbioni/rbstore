package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

/**
 * Created by renan on 05/04/2017.
 */
public class ProductCheckout {

    private final Product product;

    private final BigDecimal discounts;

    @JsonCreator
    public ProductCheckout(Product product, BigDecimal discounts) {
        this.product = product;
        this.discounts = discounts;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getDiscounts() {
        return discounts;
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(this.product.getPrice().doubleValue() - (product.getPrice().doubleValue() * discounts.doubleValue()));
    }
}