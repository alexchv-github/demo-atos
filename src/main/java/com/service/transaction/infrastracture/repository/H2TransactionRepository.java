package com.service.transaction.infrastracture.repository;

import java.util.List;

import com.service.transaction.domain.model.Transaction;
import com.service.transaction.domain.repository.TransactionRepository;
import com.service.transaction.infrastracture.repository.mapper.TransactionMapper;
import org.springframework.stereotype.Component;

@Component
public class H2TransactionRepository implements TransactionRepository {

    private H2TransactionRepositoryCrud repositoryCrud;
    private TransactionMapper mapper;

    public H2TransactionRepository(H2TransactionRepositoryCrud repositoryCrud, TransactionMapper mapper) {
        this.repositoryCrud = repositoryCrud;
        this.mapper = mapper;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        repositoryCrud.save(mapper.transactionToDbTransaction(transaction));
    }

    @Override
    public Transaction searchTransactionByReference(String reference) {
        return mapper.dbTransactionToTransaction(repositoryCrud.getByReference(reference));
    }

    @Override
    public List<Transaction> searchTransactionByIban(String iban, String order) {
        if (order.equalsIgnoreCase("DESC"))
            return mapper.dbTransactionsToTransactions(repositoryCrud.getByIbanOrderByDateDesc(iban));

        return mapper.dbTransactionsToTransactions(repositoryCrud.getByIbanOrderByDateAsc(iban));
    }

    @Override
    public Transaction searchTransactionStatus(String reference) {
        return mapper.dbTransactionToTransaction(repositoryCrud.getByReference(reference));
    }
}
