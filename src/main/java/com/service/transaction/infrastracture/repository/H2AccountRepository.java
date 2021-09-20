package com.service.transaction.infrastracture.repository;

import com.service.transaction.domain.model.Account;
import com.service.transaction.domain.repository.OrderAccount;
import com.service.transaction.infrastracture.repository.mapper.AccountMapper;
import org.springframework.stereotype.Component;

@Component
public class H2AccountRepository implements OrderAccount {

    private H2AccountRepositoryCrud repositoryCrud;
    private AccountMapper mapper;

    public H2AccountRepository(H2AccountRepositoryCrud repositoryCrud, AccountMapper mapper) {
        this.repositoryCrud = repositoryCrud;
        this.mapper = mapper;
    }

    @Override
    public Account findByIban(String iban) {
        return mapper.dbAccountToAccount(repositoryCrud.findByIban(iban));
    }

    @Override
    public void modifyBalance(Account account) {
        repositoryCrud.save(mapper.accountToDbAccount(account));
    }


}
