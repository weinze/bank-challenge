package com.weinze.bank.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${CLIENT_SERVICE_URL:http://localhost:8081}")
    private String CLIENT_SERVICE_URL;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().baseUrl(CLIENT_SERVICE_URL);
    }
}

