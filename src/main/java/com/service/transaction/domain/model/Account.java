package com.service.transaction.domain.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class Account {

    @NotBlank
    private BigDecimal balance;

    @NotBlank
    private String iban;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
