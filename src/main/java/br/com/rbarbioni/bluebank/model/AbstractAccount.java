package br.com.rbarbioni.bluebank.model;

import br.com.rbarbioni.bluebank.util.Cpf;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by renan on 13/02/17.
 */
@MappedSuperclass
public abstract class AbstractAccount extends AbstractEntity {

    @Cpf
    @NotEmpty
    @Column(name = "cpf")
    private String cpf;

    @NotEmpty
    @Column(name = "agencia")
    private String agencia;

    @NotEmpty
    @Column(name = "numero")
    private String numero;

    AbstractAccount(){
        super();
    }

    public AbstractAccount(String cpf, String agencia, String numero) {
        this();
        this.cpf = cpf;
        this.agencia = agencia;
        this.numero = numero;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null){
            return false;
        }

        if(!(obj instanceof AbstractAccount)){
            return false;
        }

        AbstractAccount account = (AbstractAccount) obj;

        return this.cpf.equals(account.getCpf()) && this.agencia.equals(account.getAgencia()) && this.numero.equals(account.getNumero());
    }
}
