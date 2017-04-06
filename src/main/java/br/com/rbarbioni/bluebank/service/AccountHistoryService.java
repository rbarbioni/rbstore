package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.AccountHistory;
import br.com.rbarbioni.bluebank.model.enums.Operation;
import br.com.rbarbioni.bluebank.repository.AccountHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by renan on 13/02/17.
 */

@Service
public class AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    public AccountHistoryService(AccountHistoryRepository accountHistoryRepository) {
        this.accountHistoryRepository = accountHistoryRepository;
    }

    public AccountHistory save(Account account, Operation operation, BigDecimal valor){
        return this.accountHistoryRepository.save(new AccountHistory(account, operation, valor));
    }

    public List<AccountHistory> find (String cpf, String agencia, String numero){
        return this.accountHistoryRepository.find(cpf, agencia, numero);
    }
}
