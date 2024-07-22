package com.weinze.bank.account.service;

import com.weinze.bank.account.domain.BankAccount;
import com.weinze.bank.account.service.dto.BankAccountDTO;
import com.weinze.bank.account.service.errors.InsufficientBalanceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.weinze.bank.account.domain.BankAccount}.
 */
public interface BankAccountService {

    /**
     * Save a bankAccount.
     *
     * @param bankAccountDTO the entity to save.
     * @return the persisted entity.
     */
    BankAccountDTO save(BankAccountDTO bankAccountDTO);

    /**
     * Updates a bankAccount.
     *
     * @param bankAccountDTO the entity to update.
     * @return the persisted entity.
     */
    BankAccountDTO update(BankAccountDTO bankAccountDTO);

    /**
     * Partially updates a bankAccount.
     *
     * @param bankAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankAccountDTO> partialUpdate(BankAccountDTO bankAccountDTO);

    /**
     * Get all the bankAccounts.
     *
     * @return the list of entities.
     */
    List<BankAccountDTO> findAll();

    /**
     * Get the "id" bankAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankAccountDTO> findOne(Long id);

    /**
     * Delete the "id" bankAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<BankAccountDTO> findAllByClient(Long client);
    BankAccount updateBalance(Long accountNumber, BigDecimal amount) throws InsufficientBalanceException;
}
