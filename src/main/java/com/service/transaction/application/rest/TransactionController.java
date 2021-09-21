package com.service.transaction.application.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.service.transaction.application.request.TransactionRequest;
import com.service.transaction.application.response.TransactionResponse;
import com.service.transaction.application.response.TransactionStatusResponse;
import com.service.transaction.domain.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class TransactionController {

    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transactions/status/{channel}/{reference}")
    public TransactionStatusResponse transactionStatus(@PathVariable @NotBlank @Size(min = 3) String channel,
            @PathVariable @NotBlank @Size(min = 3) String reference){
        return service.getTransactionStatus(channel, reference);
    }

    @GetMapping("/transactions/{iban}/{order}")
    public List<TransactionResponse> getTransactions(@PathVariable @NotBlank @Size(min = 24, max = 24) String iban, @PathVariable String order){
        return service.getTransactions(iban, order);
    }

    @PostMapping("/transactions")
    public ResponseEntity<HttpStatus> postTransactions(@RequestBody @Valid TransactionRequest transactionRequest){
        return service.postTransaction(transactionRequest);
    }

}

