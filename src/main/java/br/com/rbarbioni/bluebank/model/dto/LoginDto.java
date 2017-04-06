package br.com.rbarbioni.bluebank.model.dto;

import br.com.rbarbioni.bluebank.util.Cpf;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by renan on 12/02/17.
 */
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1797218726514184846L;

    @NotEmpty
    private String agencia;

    @NotEmpty
    private String numero;

    @NotEmpty
    @Cpf
    private String cpf;

    @NotEmpty
    private String password;

    public LoginDto(){
        super();
    }

    public LoginDto(String cpf, String agencia, String numero, String password) {
        this();
        this.agencia = agencia;
        this.numero = numero;
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumero() {
        return numero;
    }
}
