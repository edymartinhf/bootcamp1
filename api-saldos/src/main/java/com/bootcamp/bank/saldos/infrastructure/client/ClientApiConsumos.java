package com.bootcamp.bank.saldos.infrastructure.client;


import com.bootcamp.bank.saldos.infrastructure.rest.dto.CargoConsumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Component
public class ClientApiConsumos {
    @Autowired
    @Qualifier("clientConsumos")
    private WebClient webClient;

    /**
     * Permite obtener operaciones de cliente del api-creditos-consumos
     * @param numeroCredito
     * @return
     */
    public Flux<CargoConsumo> getConsumosNumeroCredito(String numeroCredito) {
        return webClient.get()
                .uri("/creditos/tarjetas/cargos/numero-credito/" + numeroCredito)
                .retrieve()
                .bodyToFlux(CargoConsumo.class);
    }

}
