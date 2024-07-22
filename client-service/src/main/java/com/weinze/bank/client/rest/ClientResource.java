package com.weinze.bank.client.rest;

import com.weinze.bank.client.repository.ClientRepository;
import com.weinze.bank.client.rest.errors.BadRequestException;
import com.weinze.bank.client.service.ClientService;
import com.weinze.bank.client.service.dto.ClientDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/clients")
public class ClientResource {

    private static final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final ClientService clientService;

    private final ClientRepository clientRepository;

    public ClientResource(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param clientDTO the clientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientDTO, or with status {@code 400 (Bad Request)} if the client has already an ID.
     */
    @PostMapping("")
    public Mono<ResponseEntity<ClientDTO>> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        log.debug("REST request to save Client : {}", clientDTO);
        if (clientDTO.getId() != null) {
            throw new BadRequestException("A new client cannot already have an ID");
        }

        return Mono.fromCallable(() -> clientService.save(clientDTO))
                .map(client -> ResponseEntity.status(CREATED).body(client));
    }

    /**
     * {@code PUT  /clients/:id} : Updates an existing client.
     *
     * @param id the id of the clientDTO to save.
     * @param clientDTO the clientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDTO,
     * or with status {@code 400 (Bad Request)} if the clientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientDTO>> updateClient(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClientDTO clientDTO
    ) {
        log.debug("REST request to update Client : {}, {}", id, clientDTO);
        if (clientDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, clientDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }

        if (!clientRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        return Mono.fromCallable(() -> clientService.update(clientDTO))
                .map(client -> ResponseEntity.ok().body(client));
    }

    /**
     * {@code PATCH  /clients/:id} : Partial updates given fields of an existing client, field will ignore if it is null
     *
     * @param id the id of the clientDTO to save.
     * @param clientDTO the clientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDTO,
     * or with status {@code 400 (Bad Request)} if the clientDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clientDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}")
    public Mono<ResponseEntity<ClientDTO>> partialUpdateClient(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClientDTO clientDTO
    ) {
        log.debug("REST request to partial update Client partially : {}, {}", id, clientDTO);
        if (clientDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, clientDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }

        if (!clientRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        return Mono.fromCallable(() -> clientService.partialUpdate(clientDTO))
                .flatMap(optionalClient -> optionalClient
                                .map(client -> Mono.just(ResponseEntity.ok(client)))
                                .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))
                );
    }

    /**
     * {@code GET  /clients} : get all the clients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clients in body.
     */
    @GetMapping("")
    public Flux<ClientDTO> getAllClients() {
        log.debug("REST request to get all Clients");
        return Mono.fromCallable(clientService::findAll).flatMapMany(Flux::fromIterable);
    }

    /**
     * {@code GET  /clients/:id} : get the "id" client.
     *
     * @param id the id of the clientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientDTO>> getClient(@PathVariable("id") Long id) {
        log.debug("REST request to get Client : {}", id);
        return Mono.fromCallable(() -> clientService.findOne(id))
                .flatMap(optionalClient -> optionalClient
                        .map(client -> Mono.just(ResponseEntity.ok(client)))
                        .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))
                );
    }

    /**
     * {@code DELETE  /clients/:id} : delete the "id" client.
     *
     * @param id the id of the clientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable("id") Long id) {
        log.debug("REST request to delete Client : {}", id);
        return Mono.fromRunnable(() -> clientService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
