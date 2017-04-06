package br.com.rbarbioni.bluebank.repository;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by renan on 10/02/2017.
 */
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    @Query("SELECT c FROM #{#entityName} c WHERE c.cpf=?1 AND c.agencia=?2 AND c.numero=?3 AND c.createdDate between ?4 and ?5")
    List<AccountHistory> find(String cpf, String agencia, String numero, Date inicial, Date fim);

    @Query("SELECT c FROM #{#entityName} c WHERE c.cpf=?1 AND c.agencia=?2 AND c.numero=?3")
    List<AccountHistory> find(String cpf, String agencia, String numero);

}
