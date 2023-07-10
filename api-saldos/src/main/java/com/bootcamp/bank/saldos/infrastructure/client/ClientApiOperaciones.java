package com.bootcamp.bank.saldos.infrastructure.client;


import com.bootcamp.bank.saldos.infrastructure.rest.dto.OperacionCta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Clase cliente permite obtener operaciones por id cliente
 */
@Component
public class ClientApiOperaciones {
    @Autowired
    @Qualifier("clientOperaciones")
    private WebClient webClient;

    /**
     * Permite obtener operaciones de cliente del api-cuentas-operaciones
     * @param numeroCuenta
     * @return
     */
    public Flux<OperacionCta> getOperacionesPorNumeroCuenta(String numeroCuenta,String tipoOperacion) {
        return webClient.get()
                .uri("/operaciones/cuentas/cuenta/" + numeroCuenta+"/tipo/"+tipoOperacion)
                .retrieve()
                .bodyToFlux(OperacionCta.class);
    }
}
