package com.service.transaction.domain.mapper;

import java.util.List;

import com.service.transaction.application.request.TransactionRequest;
import com.service.transaction.application.response.TransactionResponse;
import com.service.transaction.application.response.TransactionStatusResponse;
import com.service.transaction.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface DomainTransactionMapper {

    TransactionStatusResponse transactionToTransactionStatusResponse(Transaction transaction);

    Transaction transactionRequestToTransaction(TransactionRequest request);

    List<TransactionResponse> transactionListToTransactionResponseList(List<Transaction> transactions);

}
