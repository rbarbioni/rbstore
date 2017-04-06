package br.com.rbarbioni.bluebank.repository;

import br.com.rbarbioni.bluebank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by renan on 10/02/2017.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT c FROM #{#entityName} c WHERE c.cpf=?1 AND c.agencia=?2 AND c.numero=?3")
    Account findUnique(String cpf, String agencia, String numero);

    @Query("SELECT c FROM #{#entityName} c WHERE c.cpf=?1")
    Account findUnique(String cpf);

}
