package com.service.transaction.application.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.service.transaction.application.request.TransactionRequest;
import com.service.transaction.application.response.TransactionStatusResponse;
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

    @GetMapping("/transactions/{channel}/{reference}")
    TransactionStatusResponse transactionStatus(@PathVariable String channel, @PathVariable @NotBlank String reference){
        return null;
    }

    @GetMapping("/transactions/{iban}/{sort}")
    TransactionStatusResponse getTransactions(@PathVariable String iban, @PathVariable String sort){
        return null;
    }

    @PostMapping("/transactions}")
    ResponseEntity<?> postTransactions(@RequestBody @Valid TransactionRequest transactionRequest){
        return null;
    }

}

