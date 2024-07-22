package com.weinze.bank.account.service.impl;

import com.weinze.bank.account.domain.BankAccount;
import com.weinze.bank.account.domain.Transaction;
import com.weinze.bank.account.repository.TransactionRepository;
import com.weinze.bank.account.service.BankAccountService;
import com.weinze.bank.account.service.TransactionService;
import com.weinze.bank.account.service.dto.TransactionDTO;
import com.weinze.bank.account.service.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link com.weinze.bank.account.domain.Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final BankAccountService bankAccountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, BankAccountService bankAccountService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        log.debug("Request to save Transaction : {}", transactionDTO);
        final BankAccount account = bankAccountService.updateBalance(transactionDTO.getAccountNumber(), transactionDTO.getAmount());

        final Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setAccount(account);
        transaction.balance(account.getCurrentBalance());

        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDTO update(TransactionDTO transactionDTO) {
        log.debug("Request to update Transaction : {}", transactionDTO);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    @Override
    public Optional<TransactionDTO> partialUpdate(TransactionDTO transactionDTO) {
        log.debug("Request to partially update Transaction : {}", transactionDTO);

        return transactionRepository
            .findById(transactionDTO.getId())
            .map(existingTransaction -> {
                transactionMapper.partialUpdate(existingTransaction, transactionDTO);

                return existingTransaction;
            })
            .map(transactionRepository::save)
            .map(transactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll().stream().map(transactionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id).map(transactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionDTO> filterByClientAndDate(Long client, Instant startDate, Instant endDate) {
        log.debug("Request to get all Transactions by client : {}", client);
        return transactionRepository.findAllByAccountClientAndDateBetween(client, startDate, endDate)
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }
}
