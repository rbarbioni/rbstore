package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by renan on 08/04/17.
 */
public class Phone implements Serializable {

    private final String countryCode;

    private final String areaCode;

    private final String number;


    @JsonCreator
    public Phone(@JsonProperty("countryCode") String countryCode, @JsonProperty("areaCode") String areaCode, @JsonProperty("number") String number) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getNumber() {
        return number;
    }
}
