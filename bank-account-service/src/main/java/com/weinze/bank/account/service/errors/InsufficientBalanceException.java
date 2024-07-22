package com.weinze.bank.account.service.errors;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
    private final Long account;
    private final BigDecimal currentBalance;
    private final BigDecimal amount;

    public InsufficientBalanceException(Long account, BigDecimal currentBalance, BigDecimal amount) {
        super("Insufficient balance");
        this.account = account;
        this.currentBalance = currentBalance;
        this.amount = amount;
    }

    public Long getAccount() {
        return account;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "InsufficientBalanceException{" +
                "account=" + account +
                ", currentBalance=" + currentBalance +
                ", amount=" + amount +
                '}';
    }
}
