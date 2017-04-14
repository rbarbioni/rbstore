package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by renan on 08/04/17.
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = -3071066428687471731L;

    private final Long id;

    private final String email;

    private final String password;

    private final String paymentId;

    @JsonCreator
    public Customer(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("paymentId") String paymentId) {

        this.id = 1L;
        this.email = email;
        this.password = password;
        this.paymentId = paymentId;
    }

    public String getEmail() {
        return email;
    }

    public String getPaymentId() {
        return paymentId;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonGetter
    public String getOwnId (){
        return this.id.toString();
    }
}