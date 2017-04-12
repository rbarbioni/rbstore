package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by renan on 08/04/17.
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = -3071066428687471731L;

    private final Long id;

    private final String name;

    private final String fullname;

    private final String email;

    private final String password;

    private final Date birthdate;

    private final TaxDocument taxDocument;

    private final Phone phone;

    public Customer(
            @JsonProperty("name") String name,
            @JsonProperty("fullname") String fullname,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd") @JsonProperty("birthdate") Date birthdate,
            @JsonProperty("taxDocument") TaxDocument taxDocument,
            @JsonProperty("phone") Phone phone) {
        this.id = 1L;
        this.name = name;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.birthdate = birthdate;
        this.taxDocument = taxDocument;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public TaxDocument getTaxDocument() {
        return taxDocument;
    }

    public Phone getPhone() {
        return phone;
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