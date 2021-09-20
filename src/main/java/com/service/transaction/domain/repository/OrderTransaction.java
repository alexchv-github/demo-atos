package com.service.transaction.domain.repository;

import java.util.List;

import com.service.transaction.domain.model.Transaction;

public interface OrderTransaction {

    void createTransaction(Transaction transaction);

    Transaction searchTransactionByReference(String reference);

    List<Transaction> searchTransactionByIban(String iban);

    Transaction searchTransactionStatus(String reference);

}
