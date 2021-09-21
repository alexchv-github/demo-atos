package com.service.transaction.domain.service;

import java.util.List;
import java.util.UUID;

import com.service.transaction.application.request.TransactionRequest;
import com.service.transaction.application.response.TransactionResponse;
import com.service.transaction.application.response.TransactionStatusResponse;
import com.service.transaction.domain.mapper.DomainTransactionMapper;
import com.service.transaction.domain.model.Account;
import com.service.transaction.domain.model.Channel;
import com.service.transaction.domain.model.StatusEnum;
import com.service.transaction.domain.repository.AccountRepository;
import com.service.transaction.domain.repository.ChannelRepository;
import com.service.transaction.domain.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DomainTransactionService implements TransactionService{

    private DomainTransactionMapper mapper;
    private TransactionRepository repository;
    private AccountRepository accountRepository;
    private ChannelRepository channelRepository;

    public DomainTransactionService(DomainTransactionMapper mapper, TransactionRepository repository, AccountRepository accountRepository,
            ChannelRepository channelRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public TransactionStatusResponse getTransactionStatus(String channel, String reference) {
        TransactionStatusResponse response = mapper.transactionToTransactionStatusResponse(repository.searchTransactionStatus(reference));
        Channel dbChannel = channelRepository.findByChannel(channel);

        if (response == null || dbChannel == null){
            response = new TransactionStatusResponse();
            response.setReference(reference);
            response.setStatus(StatusEnum.INVALID.name());

            return response;
        }

        if (Boolean.TRUE.equals(dbChannel.getSubtract())){
            response.setAmount(response.getAmount().subtract(response.getFee()));
            response.setFee(null);
        }

        return response;
    }

    @Override
    public ResponseEntity<HttpStatus> postTransaction(TransactionRequest request) {
        Account account = accountRepository.findByIban(request.getIban());

        if (request.getAmount().signum() < 0 && (account.getBalance().add(request.getAmount()).floatValue() < 0))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        if (request.getReference() == null || request.getReference().isEmpty())
            request.setReference(UUID.randomUUID().toString());

        request.setAmount(account.getBalance().add(request.getAmount()));
        request.setStatus(StatusEnum.PENDING.name());

        repository.createTransaction(mapper.transactionRequestToTransaction(request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<TransactionResponse> getTransactions(String iban, String order) {
        return mapper.transactionListToTransactionResponseList(repository.searchTransactionByIban(iban, order));
    }
}
