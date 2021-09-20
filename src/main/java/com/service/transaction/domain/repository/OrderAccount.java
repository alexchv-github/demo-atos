package com.service.transaction.domain.repository;

import com.service.transaction.domain.model.Account;

public interface OrderAccount {

    Account findByIban(String iban);

    void modifyBalance(Account account);

}
