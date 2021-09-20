package com.service.transaction.infrastracture.repository.mapper;

import java.util.List;

import com.service.transaction.domain.model.Transaction;
import com.service.transaction.infrastracture.repository.entity.DbTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface TransactionMapper {

    DbTransaction transactionToDbTransaction(Transaction transaction);

    Transaction dbTransactionToTransaction(DbTransaction dbTransaction);

    List<Transaction> dbTransactionsToTransactions(List<DbTransaction> dbTransaction);
}
