package com.weinze.bank.account.service.dto;

import com.weinze.bank.account.domain.enums.AccountType;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private Long number;

    @NotNull
    private AccountType type;

    @NotNull
    private BigDecimal initialBalance;

    @NotNull
    private BigDecimal currentBalance;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Long client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "id=" + id +
                ", number=" + number +
                ", type=" + type +
                ", initialBalance=" + initialBalance +
                ", currentBalance=" + currentBalance +
                ", enabled=" + enabled +
                ", client=" + client +
                '}';
    }
}
