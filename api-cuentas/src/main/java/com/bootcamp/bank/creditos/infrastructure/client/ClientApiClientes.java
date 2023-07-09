package com.bootcamp.bank.creditos.infrastructure.client;

import com.bootcamp.bank.creditos.infrastructure.rest.dto.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientApiClientes {
    @Autowired
    @Qualifier("clientClientes")
    private WebClient webClient;

    /**
     * Permite obtener informacion de cliente del api-clientes
     * @param idCliente
     * @return
     */

    public Mono<Cliente> getClientes(String idCliente) {
        return webClient.get()
                .uri("/clientes/" + idCliente)
                .retrieve()
                .bodyToMono(Cliente.class);
    }
}
