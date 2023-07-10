package com.bootcamp.bank.operaciones.application;

import com.bootcamp.bank.operaciones.application.util.Util;
import com.bootcamp.bank.operaciones.infrastructure.repository.OperacionesCuentaRepository;
import com.bootcamp.bank.operaciones.infrastructure.repository.dao.OperacionCtaDao;
import com.bootcamp.bank.operaciones.infrastructure.rest.dto.OperacionCta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Clase Operaciones Ccuentas
 *
 * */

@Component
@RequiredArgsConstructor
@Slf4j
public class OperacionesCuentasUseCase {

    private final OperacionesCuentaRepository operacionesCuentaRepository;

    /**
     * Permite Registrar una operacion cuenta bancaria DEP-RETIRO
     * @param operationCtaDao
     * @return
     */
    public Mono<OperacionCtaDao> save(OperacionCtaDao operationCtaDao){
        operationCtaDao = operacionCta.apply(operationCtaDao);
        return operacionesCuentaRepository.save(operationCtaDao);

    }

    public Flux<OperacionCtaDao> findAll(){
        return operacionesCuentaRepository.findAll();
    }

    public Flux<OperacionCtaDao> findByNumeroCuenta(String numeroCuenta) {
        return operacionesCuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    public Flux<OperacionCtaDao> findByNumeroCuentaAndTipoOperacion(String numeroCuenta,String tipoOperacion) {
        return operacionesCuentaRepository.findByNumeroCuentaAndTipoOperacion(numeroCuenta,tipoOperacion);
    }

    Function<OperacionCtaDao,OperacionCtaDao> operacionCta = cta -> {
        cta.setFechaOperacion(Util.getCurrentDateAsString("dd/MM/yyyy"));
        return cta;
    };

}
