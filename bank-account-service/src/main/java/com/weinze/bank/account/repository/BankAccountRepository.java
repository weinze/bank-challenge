package com.weinze.bank.account.repository;

import com.weinze.bank.account.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByNumber(Long number);
    List<BankAccount> findAllByClient(Long client);

}
