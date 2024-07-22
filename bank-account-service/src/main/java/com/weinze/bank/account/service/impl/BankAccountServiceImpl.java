package com.weinze.bank.account.service.impl;

import com.weinze.bank.account.domain.BankAccount;
import com.weinze.bank.account.repository.BankAccountRepository;
import com.weinze.bank.account.service.BankAccountService;
import com.weinze.bank.account.service.dto.BankAccountDTO;
import com.weinze.bank.account.service.errors.InsufficientBalanceException;
import com.weinze.bank.account.service.mapper.BankAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;

    private final BankAccountMapper bankAccountMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        log.debug("Request to save BankAccount : {}", bankAccountDTO);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public BankAccountDTO update(BankAccountDTO bankAccountDTO) {
        log.debug("Request to update BankAccount : {}", bankAccountDTO);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public Optional<BankAccountDTO> partialUpdate(BankAccountDTO bankAccountDTO) {
        log.debug("Request to partially update BankAccount : {}", bankAccountDTO);

        return bankAccountRepository
                .findById(bankAccountDTO.getId())
                .map(existingBankAccount -> {
                    bankAccountMapper.partialUpdate(existingBankAccount, bankAccountDTO);

                    return existingBankAccount;
                })
                .map(bankAccountRepository::save)
                .map(bankAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO> findAll() {
        log.debug("Request to get all BankAccounts");
        return bankAccountRepository.findAll().stream().map(bankAccountMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankAccountDTO> findOne(Long id) {
        log.debug("Request to get BankAccount : {}", id);
        return bankAccountRepository.findById(id).map(bankAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankAccount : {}", id);
        bankAccountRepository.deleteById(id);
    }

    public BankAccount updateBalance(Long accountNumber, BigDecimal amount) throws InsufficientBalanceException {
        return bankAccountRepository.findByNumber(accountNumber).map(bankAccount -> {

            final BigDecimal result = bankAccount.getCurrentBalance().add(amount);
            if (result.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException(
                        bankAccount.getNumber(), bankAccount.getCurrentBalance(), amount
                );
            }

            return bankAccount.currentBalance(result);
        }).orElseThrow(() -> new RuntimeException("Invalid account"));
    }
}
