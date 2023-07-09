package com.bootcamp.bank.saldos.infrastructure.client;

import com.bootcamp.bank.saldos.infrastructure.rest.dto.CreditoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ClientApiCreditos {

    @Autowired
    @Qualifier("clientCreditos")
    private WebClient webClient;

    /**
     * Permite obtener creditos de cliente del api-creditos
     * @param idCliente
     * @return
     */
    public Flux<CreditoProducto> getCreditos(String idCliente) {
        return webClient.get()
                .uri("/creditos/cliente/" + idCliente)
                .retrieve()
                .bodyToFlux(CreditoProducto.class);
    }

}
