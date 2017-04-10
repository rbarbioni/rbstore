package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by renan on 08/04/17.
 */
public class MoipOrderRequest implements Serializable {

    private static final long serialVersionUID = -5728695275552061335L;

    private final String ownId;

    private final Collection<Product> itens;

    private final Customer customer;

    @JsonCreator
    public MoipOrderRequest(Collection<Product> itens, Customer customer) {
        this.ownId = UUID.randomUUID().toString();
        this.itens = itens;
        this.customer = customer;
    }

    public String getOwnId() {
        return ownId;
    }

    public Collection<Product> getItens() {
        return itens;
    }

    public Customer getCustomer() {
        return customer;
    }
}
