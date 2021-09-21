package com.service.transaction.domain.service;

import com.service.transaction.application.response.TransactionStatusResponse;
import com.service.transaction.domain.mapper.DomainTransactionMapper;
import com.service.transaction.domain.model.Channel;
import com.service.transaction.domain.model.StatusEnum;
import com.service.transaction.domain.repository.ChannelRepository;
import com.service.transaction.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class DomainTransactionService implements TransactionService{

    private DomainTransactionMapper mapper;
    private TransactionRepository repository;
    private ChannelRepository channelRepository;

    public DomainTransactionService(DomainTransactionMapper mapper, TransactionRepository repository, ChannelRepository channelRepository) {
        this.mapper = mapper;
        this.repository = repository;
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

        if (dbChannel.getSubtract()){
            response.setAmount(response.getAmount().subtract(response.getFee()));
            response.setFee(null);
        }


        return response;
    }
}
