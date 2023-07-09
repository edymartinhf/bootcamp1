package com.bootcamp.bank.saldos.infrastructure.client;

import com.bootcamp.bank.saldos.infrastructure.rest.dto.CreditoProducto;
import com.bootcamp.bank.saldos.infrastructure.rest.dto.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ClientApiCuentas {
    @Autowired
    @Qualifier("clientCuentas")
    private WebClient webClient;

    /**
     * Permite obtener cuentas de cliente del api-cuentas
     * @param idCliente
     * @return
     */
    public Flux<Cuenta> getCuentas(String idCliente) {
        return webClient.get()
                .uri("/cuentas/cliente/" + idCliente)
                .retrieve()
                .bodyToFlux(Cuenta.class);
    }
}
