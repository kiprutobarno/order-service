package com.bookshop.orderservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CllientConfig {
    @Bean
    WebClient WebClient(ClientProperties clientProperties, WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(clientProperties.catalogServiceUri().toString()).build();
    }
}
