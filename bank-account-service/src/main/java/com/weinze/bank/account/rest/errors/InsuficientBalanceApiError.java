package com.weinze.bank.account.rest.errors;

import com.weinze.bank.account.service.errors.InsufficientBalanceException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class InsuficientBalanceApiError extends ApiError {

    private static final String MESSAGE = "Saldo no disponible";

    private final Long account;
    private final BigDecimal currentBalance;
    private final BigDecimal amount;

    public InsuficientBalanceApiError(InsufficientBalanceException ex) {
        this(ex.getAccount(), ex.getCurrentBalance(), ex.getAmount());
    }

    public InsuficientBalanceApiError(Long account, BigDecimal currentBalance, BigDecimal amount) {
        super(HttpStatus.BAD_REQUEST.value(), MESSAGE);
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
}
