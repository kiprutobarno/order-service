package com.bookshop.orderservice.Config;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "bookshop")
public record ClientProperties(@NotNull URI catalogServiceUri) {

}
