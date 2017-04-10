package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by renan on 05/04/2017.
 */
public class Product implements Serializable {

    private final Long id;
    private final String name;
    private final String image;
    private final String detail;
    private final BigDecimal price;
    private final Integer ratting;
    private final Integer quantity;

    @JsonCreator
    public Product(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("image") String image,
            @JsonProperty("detail") String detail,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("ratting") Integer ratting,
            @JsonProperty("quantity") Integer quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.price = price;
        this.ratting = ratting;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDetail() {
        return detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getRatting() {
        return ratting;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @JsonGetter
    public String getProduct (){
        return this.name;
    }
}