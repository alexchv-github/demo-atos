package com.service.transaction.domain.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class Account {

    @NotBlank
    private BigDecimal balance;

    @NotBlank
    private String iban;

}
