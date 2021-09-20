package com.service.transaction.infrastracture.repository;

import com.service.transaction.infrastracture.repository.entity.DbAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2AccountRepositoryCrud extends CrudRepository<DbAccount, Long> {

    DbAccount findByIban(String iban);
}
