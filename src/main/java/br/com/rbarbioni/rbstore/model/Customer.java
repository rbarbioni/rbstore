package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by renan on 08/04/17.
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = -3071066428687471731L;

    private final String name;

    private final String fullName;

    private final String email;

    private final Date bithDate;

    private final TaxDocument taxDocument;

    private final Phone phone;

    public Customer(
            @JsonProperty String name,
            @JsonProperty String fullName,
            @JsonProperty String email,
            @JsonProperty Date bithDate,
            @JsonProperty TaxDocument taxDocument,
            @JsonProperty Phone phone) {
        this.name = name;
        this.fullName = fullName;
        this.email = email;
        this.bithDate = bithDate;
        this.taxDocument = taxDocument;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Date getBithDate() {
        return bithDate;
    }

    public TaxDocument getTaxDocument() {
        return taxDocument;
    }

    public Phone getPhone() {
        return phone;
    }
}