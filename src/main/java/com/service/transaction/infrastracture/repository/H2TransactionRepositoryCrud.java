package com.service.transaction.infrastracture.repository;

import java.util.List;

import com.service.transaction.infrastracture.repository.entity.DbTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2TransactionRepositoryCrud extends CrudRepository<DbTransaction, Long> {

    DbTransaction getByReference(String reference);

    List<DbTransaction> getByIban(String iban);

}
