package com.weinze.bank.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weinze.bank.account.domain.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @NotNull
    @Column(name = "initial_balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal initialBalance;

    @NotNull
    @Column(name = "current_balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal currentBalance;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "client", nullable = false)
    private Long client;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnoreProperties(value = { "account" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

    public Long getId() {
        return this.id;
    }

    public BankAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public BankAccount number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public AccountType getType() {
        return this.type;
    }

    public BankAccount type(AccountType type) {
        this.setType(type);
        return this;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getInitialBalance() {
        return this.initialBalance;
    }

    public BankAccount initialBalance(BigDecimal initialBalance) {
        this.setInitialBalance(initialBalance);
        return this;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getCurrentBalance() {
        return this.currentBalance;
    }

    public BankAccount currentBalance(BigDecimal currentBalance) {
        this.setCurrentBalance(currentBalance);
        return this;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public BankAccount enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getClient() {
        return this.client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public BankAccount client(Long client) {
        this.setClient(client);
        return this;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setAccount(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setAccount(this));
        }
        this.transactions = transactions;
    }

    public BankAccount transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public BankAccount addTransactions(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setAccount(this);
        return this;
    }

    public BankAccount removeTransactions(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setAccount(null);
        return this;
    }

}
