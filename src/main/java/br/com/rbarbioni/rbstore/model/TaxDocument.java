package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by renan on 08/04/17.
 */
public class TaxDocument implements Serializable {

    private static final long serialVersionUID = -726944856872774221L;

    private final DocumentType type;

    private final String number;

    @JsonCreator
    public TaxDocument(@JsonProperty("number") String number, @JsonProperty("type") DocumentType type) {
        this.number = number;
        this.type = type;
    }

    public DocumentType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }
}
