package com.service.transaction.domain.mapper;

import com.service.transaction.application.response.TransactionStatusResponse;
import com.service.transaction.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface DomainTransactionMapper {

    TransactionStatusResponse transactionToTransactionStatusResponse(Transaction transaction);

}
