package com.service.transaction.domain.repository;

import com.service.transaction.domain.model.Account;

public interface AccountRepository {

    Account findByIban(String iban);

    void modifyBalance(Account account);

}
