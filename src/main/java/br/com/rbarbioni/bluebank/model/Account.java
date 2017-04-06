package br.com.rbarbioni.bluebank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by renan on 10/02/2017.
 */

@Entity
@Table( name = "account", uniqueConstraints= @UniqueConstraint(columnNames={"cpf", "agencia", "numero"}))
@EntityListeners(AuditingEntityListener.class)
public class Account extends AbstractAccount implements Serializable, UserDetails {

    private static final long serialVersionUID = -6226618365169837926L;

    @Column(name = "saldo")
    private BigDecimal saldo = BigDecimal.ZERO;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Transient
    private String token;

    public Account(){
        super();
    }

    public Account(String cpf, String agencia, String numero) {
        super(cpf, agencia, numero);
    }

    public Account(String cpf, String agencia, String numero, String password) {
        this(cpf, agencia, numero);
        this.password = password;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void sacar(BigDecimal valor){
        this.saldo = saldo.subtract(valor);
    }

    public void depositar (BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return getCpf();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}