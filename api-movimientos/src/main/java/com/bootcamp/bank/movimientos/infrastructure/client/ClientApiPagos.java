package com.bootcamp.bank.movimientos.infrastructure.client;

import com.bootcamp.bank.movimientos.infrastructure.rest.dto.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ClientApiPagos {

    @Autowired
    @Qualifier("clientPagos")
    private WebClient webClient;

    /**
     * Permite obtener operaciones de cliente del api-creditos-pagos
     * @param idCliente
     * @return
     */
    public Flux<Pago> getPagos(String idCliente) {
        return webClient.get()
                .uri("/creditos/pago/" + idCliente)
                .retrieve()
                .bodyToFlux(Pago.class);
    }
}
