package com.weinze.jhipster.test2.web.rest;

import com.weinze.jhipster.test2.repository.BankAccountRepository;
import com.weinze.jhipster.test2.service.BankAccountService;
import com.weinze.jhipster.test2.service.dto.BankAccountDTO;
import com.weinze.jhipster.test2.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.weinze.jhipster.test2.domain.BankAccount}.
 */
@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {

    private static final Logger log = LoggerFactory.getLogger(BankAccountResource.class);

    private static final String ENTITY_NAME = "bankAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankAccountService bankAccountService;

    private final BankAccountRepository bankAccountRepository;

    public BankAccountResource(BankAccountService bankAccountService, BankAccountRepository bankAccountRepository) {
        this.bankAccountService = bankAccountService;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * {@code POST  /bank-accounts} : Create a new bankAccount.
     *
     * @param bankAccountDTO the bankAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankAccountDTO, or with status {@code 400 (Bad Request)} if the bankAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BankAccountDTO> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) throws URISyntaxException {
        log.debug("REST request to save BankAccount : {}", bankAccountDTO);
        if (bankAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bankAccountDTO = bankAccountService.save(bankAccountDTO);
        return ResponseEntity.created(new URI("/api/bank-accounts/" + bankAccountDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bankAccountDTO.getId().toString()))
            .body(bankAccountDTO);
    }

    /**
     * {@code PUT  /bank-accounts/:id} : Updates an existing bankAccount.
     *
     * @param id the id of the bankAccountDTO to save.
     * @param bankAccountDTO the bankAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankAccountDTO bankAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankAccount : {}, {}", id, bankAccountDTO);
        if (bankAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankAccountDTO = bankAccountService.update(bankAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankAccountDTO.getId().toString()))
            .body(bankAccountDTO);
    }

    /**
     * {@code PATCH  /bank-accounts/:id} : Partial updates given fields of an existing bankAccount, field will ignore if it is null
     *
     * @param id the id of the bankAccountDTO to save.
     * @param bankAccountDTO the bankAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankAccountDTO> partialUpdateBankAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankAccountDTO bankAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankAccount partially : {}, {}", id, bankAccountDTO);
        if (bankAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankAccountDTO> result = bankAccountService.partialUpdate(bankAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-accounts} : get all the bankAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankAccounts in body.
     */
    @GetMapping("")
    public List<BankAccountDTO> getAllBankAccounts() {
        log.debug("REST request to get all BankAccounts");
        return bankAccountService.findAll();
    }

    /**
     * {@code GET  /bank-accounts/:id} : get the "id" bankAccount.
     *
     * @param id the id of the bankAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable("id") Long id) {
        log.debug("REST request to get BankAccount : {}", id);
        Optional<BankAccountDTO> bankAccountDTO = bankAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankAccountDTO);
    }

    /**
     * {@code DELETE  /bank-accounts/:id} : delete the "id" bankAccount.
     *
     * @param id the id of the bankAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable("id") Long id) {
        log.debug("REST request to delete BankAccount : {}", id);
        bankAccountService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
