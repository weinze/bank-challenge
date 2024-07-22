package com.weinze.bank.account.rest;

import com.weinze.bank.account.repository.TransactionRepository;
import com.weinze.bank.account.rest.errors.BadRequestException;
import com.weinze.bank.account.service.TransactionService;
import com.weinze.bank.account.service.dto.TransactionDTO;
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
@RequestMapping("/api/transactions")
public class TransactionResource {

    private static final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    public TransactionResource(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    /**
     * {@code POST  /transactions} : Create a new transaction.
     *
     * @param transactionDTO the transactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDTO, or with status {@code 400 (Bad Request)} if the transaction has already an ID.
     */
    @PostMapping("")
    public Mono<ResponseEntity<TransactionDTO>> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        log.debug("REST request to save Transaction : {}", transactionDTO);
        if (transactionDTO.getId() != null) {
            throw new BadRequestException("A new transaction cannot already have an ID");
        }

        return Mono.fromCallable(() -> transactionService.save(transactionDTO))
                .map(transaction -> ResponseEntity.status(CREATED).body(transaction));
    }

    /**
     * {@code PUT  /transactions/:id} : Updates an existing transaction.
     *
     * @param id the id of the transactionDTO to save.
     * @param transactionDTO the transactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<TransactionDTO>> updateTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransactionDTO transactionDTO
    ) {
        log.debug("REST request to update Transaction : {}, {}", id, transactionDTO);
        if (transactionDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, transactionDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }

        if (!transactionRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        return Mono.fromCallable(() -> transactionService.update(transactionDTO))
                .map(transaction -> ResponseEntity.ok().body(transaction));
    }

    /**
     * {@code PATCH  /transactions/:id} : Partial updates given fields of an existing transaction, field will ignore if it is null
     *
     * @param id the id of the transactionDTO to save.
     * @param transactionDTO the transactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TransactionDTO>> partialUpdateTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransactionDTO transactionDTO
    ) {
        log.debug("REST request to partial update Transaction partially : {}, {}", id, transactionDTO);
        if (transactionDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, transactionDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }

        if (!transactionRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        return Mono.fromCallable(() -> transactionService.partialUpdate(transactionDTO))
                .flatMap(optionalTransaction -> optionalTransaction
                        .map(transaction -> Mono.just(ResponseEntity.ok(transaction)))
                        .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))
                );
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("")
    public Flux<TransactionDTO> getAllTransactions() {
        log.debug("REST request to get all Transactions");
        return Mono.fromCallable(transactionService::findAll).flatMapMany(Flux::fromIterable);
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transaction.
     *
     * @param id the id of the transactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionDTO>> getTransaction(@PathVariable("id") Long id) {
        log.debug("REST request to get Transaction : {}", id);
        return Mono.fromCallable(() -> transactionService.findOne(id))
                .flatMap(optionalTransaction -> optionalTransaction
                        .map(transaction -> Mono.just(ResponseEntity.ok(transaction)))
                        .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))
                );
    }

    /**
     * {@code DELETE  /transactions/:id} : delete the "id" transaction.
     *
     * @param id the id of the transactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable("id") Long id) {
        log.debug("REST request to delete Transaction : {}", id);
        return Mono.fromRunnable(() -> transactionService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
