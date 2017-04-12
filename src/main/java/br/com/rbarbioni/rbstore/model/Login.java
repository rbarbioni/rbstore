package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by renan on 10/04/17.
 */
public class Login implements Serializable{

    private static final long serialVersionUID = 9066861046505848513L;

    private final String email;

    private final String password;

    @JsonCreator
    public Login(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}