package com.bootcamp.bank.cuentas.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiClienteConfig {
    @Bean("clientClientes")
    public WebClient webClient() {
        return WebClient.create("http://localhost:8081"); // Cambia la URL y el puerto según tu API REST local
    }
}
