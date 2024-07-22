package com.weinze.bank.account.repository;

import com.weinze.bank.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountClientAndDateBetween(Long client, Instant startDate, Instant endDate);
}
