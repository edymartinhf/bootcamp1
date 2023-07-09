package com.bootcamp.bank.movimientos.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase configuracion clientes
 */
@Configuration
public class WebClientConfig {

    @Bean(name = "clientConsumos")
    public WebClient webClientConsumos() {
        return WebClient.create("http://localhost:8085");
    }

    @Bean(name = "clientOperaciones")
    public WebClient webClientOperaciones() {
        return WebClient.create("http://localhost:8084");
    }

    @Bean(name = "clientPagos")
    public WebClient webClientPagos() {
        return WebClient.create("http://localhost:8086");
    }
}
