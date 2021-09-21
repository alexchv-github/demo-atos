package com.service.transaction.domain.model;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance;

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
