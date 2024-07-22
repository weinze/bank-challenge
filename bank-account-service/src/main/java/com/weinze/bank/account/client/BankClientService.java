package com.weinze.bank.account.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class BankClientService {

    private static final String CLIENT_URL = "http://localhost:8081/client/api/clients/{id}"; // TODO envars, log!!

    @Autowired
    private WebClient.Builder client;

    public Mono<Optional<ClientDTO>> getClientById(Long id) {
        /*final ClientDTO dto = new ClientDTO();
        dto.setId(id);
        return Mono.just(Optional.of(dto));*/
        return client.build()
                .get()
                .uri(CLIENT_URL, id)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(Optional.empty());
                    }
                    return Mono.error(ex);
                });
    }
}
