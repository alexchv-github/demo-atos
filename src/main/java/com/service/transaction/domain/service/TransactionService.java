package com.service.transaction.domain.service;

import com.service.transaction.application.response.TransactionStatusResponse;

public interface TransactionService {

    TransactionStatusResponse getTransactionStatus(String channel, String reference);

}
