package com.service.transaction.domain.service;

import java.util.List;

import com.service.transaction.application.request.TransactionRequest;
import com.service.transaction.application.response.TransactionResponse;
import com.service.transaction.application.response.TransactionStatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface TransactionService {

    TransactionStatusResponse getTransactionStatus(String channel, String reference);

    ResponseEntity<HttpStatus> postTransaction(TransactionRequest request);

    List<TransactionResponse> getTransactions(String iban, String order);

}
