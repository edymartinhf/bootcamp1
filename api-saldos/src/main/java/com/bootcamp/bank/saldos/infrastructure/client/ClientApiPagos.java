package com.bootcamp.bank.saldos.infrastructure.client;

import com.bootcamp.bank.saldos.infrastructure.rest.dto.Pago;
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
     * @param numeroCredito
     * @return
     */
    public Flux<Pago> getPagosNumeroCredito(String numeroCredito) {
        return webClient.get()
                .uri("/creditos/pago/numero-credito/" + numeroCredito)
                .retrieve()
                .bodyToFlux(Pago.class);
    }
}
