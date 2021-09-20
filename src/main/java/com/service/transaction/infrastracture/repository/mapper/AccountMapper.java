package com.service.transaction.infrastracture.repository.mapper;

import com.service.transaction.domain.model.Account;
import com.service.transaction.infrastracture.repository.entity.DbAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface AccountMapper {

    DbAccount accountToDbAccount(Account account);

    Account dbAccountToAccount(DbAccount dbAccount);
}
