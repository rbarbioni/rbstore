package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    private final String details;
    private final BigDecimal price;
    private final Integer ratting;

    @JsonCreator
    public Product(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("image") String image,
            @JsonProperty("details") String details,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("ratting") Integer ratting) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.details = details;
        this.price = price;
        this.ratting = ratting;
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

    public String getDetails() {
        return details;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getRatting() {
        return ratting;
    }
}