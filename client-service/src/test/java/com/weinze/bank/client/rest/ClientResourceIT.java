package com.weinze.bank.client.rest;

import com.weinze.bank.client.domain.Client;
import com.weinze.bank.client.domain.enums.Gender;
import com.weinze.bank.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ClientResourceIT {

    private static final String CLIENT_NAME = "John Doe";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void initTest() {}

    @Test
    void testGetAllClients() {
        webTestClient.get()
                .uri("/api/clients")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    void testGetAllClientsWithOneClient() {
        clientRepository.save(createEntity());

        webTestClient.get()
                .uri("/api/clients")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo(CLIENT_NAME);
    }

    public static Client createEntity() {
        return (Client) new Client()
                .clientId(1L)
                .password("A")
                .enabled(Boolean.TRUE)
                .identification(1L)
                .name(CLIENT_NAME)
                .gender(Gender.F)
                .age(1)
                .address("A")
                .phone(1L);
    }

}
