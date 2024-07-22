package com.weinze.bank.account.rest;

import com.weinze.bank.account.client.BankClientService;
import com.weinze.bank.account.repository.BankAccountRepository;
import com.weinze.bank.account.rest.errors.BadRequestException;
import com.weinze.bank.account.service.BankAccountService;
import com.weinze.bank.account.service.dto.BankAccountDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {

    private static final Logger log = LoggerFactory.getLogger(BankAccountResource.class);

    private final BankAccountService bankAccountService;

    private final BankAccountRepository bankAccountRepository;

    private final BankClientService bankClientService;

    public BankAccountResource(BankAccountService bankAccountService, BankAccountRepository bankAccountRepository, BankClientService bankClientService) {
        this.bankAccountService = bankAccountService;
        this.bankAccountRepository = bankAccountRepository;
        this.bankClientService = bankClientService;
    }

    /**
     * {@code POST  /bank-accounts} : Create a new bankAccount.
     *
     * @param bankAccountDTO the bankAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankAccountDTO, or with status {@code 400 (Bad Request)} if the bankAccount has already an ID.
     */
    @PostMapping("")
    public Mono<ResponseEntity<BankAccountDTO>> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        log.debug("REST request to save BankAccount : {}", bankAccountDTO);
        if (bankAccountDTO.getId() != null) {
            throw new BadRequestException("A new bankAccount cannot already have an ID");
        }

        return bankClientService.getClientById(bankAccountDTO.getClient())
                .flatMap(optionalClient -> {
                    if (optionalClient.isPresent()) {
                        return Mono.fromCallable(() -> bankAccountService.save(bankAccountDTO))
                                .map(account -> ResponseEntity.status(CREATED).body(account));
                    } else {
                        throw new BadRequestException("The client doesn't exists");
                    }
                });
    }

    /**
     * {@code PUT  /bank-accounts/:id} : Updates an existing bankAccount.
     *
     * @param id the id of the bankAccountDTO to save.
     * @param bankAccountDTO the bankAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<BankAccountDTO>> updateBankAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankAccountDTO bankAccountDTO
    ) {
        log.debug("REST request to update BankAccount : {}, {}", id, bankAccountDTO);
        if (bankAccountDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, bankAccountDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (bankAccountDTO.getClient() != null) {
            throw new BadRequestException("A bankAccount cannot change the client");
        }
        if (!bankAccountRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        return Mono.fromCallable(() -> bankAccountService.update(bankAccountDTO))
                .map(account -> ResponseEntity.ok().body(account));
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
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<BankAccountDTO>> partialUpdateBankAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankAccountDTO bankAccountDTO
    ) {
        log.debug("REST request to partial update BankAccount partially : {}, {}", id, bankAccountDTO);
        if (bankAccountDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, bankAccountDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (bankAccountDTO.getClient() != null) {
            throw new BadRequestException("A bankAccount cannot change the client");
        }
        if (!bankAccountRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        return Mono.fromCallable(() -> bankAccountService.partialUpdate(bankAccountDTO))
                .flatMap(optionalAccount -> optionalAccount
                        .map(account -> Mono.just(ResponseEntity.ok(account)))
                        .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))
                );
    }

    /**
     * {@code GET  /bank-accounts} : get all the bankAccounts.
     *
     * @param client the id of the client (optional).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankAccounts in body.
     */
    @GetMapping("")
    public Flux<BankAccountDTO> getAllBankAccounts(@RequestParam(required = false) Long client) {
        log.debug("REST request to get all BankAccounts");
        if (client != null) {
            return Flux.fromIterable(bankAccountService.findAllByClient(client));
        } else {
            return Flux.fromIterable(bankAccountService.findAll());
        }
    }

    /**
     * {@code GET  /bank-accounts/:id} : get the "id" bankAccount.
     *
     * @param id the id of the bankAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<BankAccountDTO>> getBankAccount(@PathVariable("id") Long id) {
        log.debug("REST request to get BankAccount : {}", id);
        return Mono.fromCallable(() -> bankAccountService.findOne(id))
                .flatMap(optionalAccount -> optionalAccount
                        .map(account -> Mono.just(ResponseEntity.ok(account)))
                        .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))
                );
    }

    /**
     * {@code DELETE  /bank-accounts/:id} : delete the "id" bankAccount.
     *
     * @param id the id of the bankAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBankAccount(@PathVariable("id") Long id) {
        log.debug("REST request to delete BankAccount : {}", id);
        return Mono.fromRunnable(() -> bankAccountService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
