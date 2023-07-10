package com.bootcamp.bank.creditos.application;

import com.bootcamp.bank.creditos.application.util.Util;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.CargoConsumoDao;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.CreditoConsumosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Clase Consumos Credito
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreditoConsumoUseCase {

    private final CreditoConsumosRepository creditoConsumosRepository;


    /**
     * Permite registrar consumos tarjeta credito
     * @param operationCtaDao
     * @return
     */
    public Mono<CargoConsumoDao> save(CargoConsumoDao operationCtaDao){
        operationCtaDao = asignarValoresCargo.apply(operationCtaDao);
        return creditoConsumosRepository.save(operationCtaDao);
    }

    Function<CargoConsumoDao,CargoConsumoDao> asignarValoresCargo = car -> {
        car.setFechaConsumo(Util.getCurrentDateAsString("dd/MM/yyyy"));
        return car;
    };

}
