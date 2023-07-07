package com.bootcamp.bank.creditos.application;

import com.bootcamp.bank.creditos.application.util.Util;
import com.bootcamp.bank.creditos.infrastructure.repository.CreditoProductoPagoRepository;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.PagoDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Clase caso uso service
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreditosPagosUseCase {

    private final CreditoProductoPagoRepository creditoProductoPagoRepository;
    public Mono<PagoDao> save(PagoDao pagoDao){
        pagoDao = pagoAsignarValores.apply(pagoDao);
        return creditoProductoPagoRepository.save(pagoDao);

    }

    Function<PagoDao,PagoDao> pagoAsignarValores = pago -> {
        pago.setFechaPago(Util.getCurrentDateAsString("dd/MM/yyyy"));
        return pago;
    };
}
