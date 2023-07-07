package com.bootcamp.bank.operaciones.application;

import com.bootcamp.bank.operaciones.application.util.Util;
import com.bootcamp.bank.operaciones.infrastructure.repository.OperacionesCuentaRepository;
import com.bootcamp.bank.operaciones.infrastructure.repository.dao.OperacionCtaDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperacionesCuentasUseCase {

    private final OperacionesCuentaRepository operacionesCuentaRepository;

    /**
     * @param operationCtaDao
     * @return
     */
    public Mono<OperacionCtaDao> save(OperacionCtaDao operationCtaDao){
        operationCtaDao = operacionCta.apply(operationCtaDao);
        return operacionesCuentaRepository.save(operationCtaDao);

    }

    Function<OperacionCtaDao,OperacionCtaDao> operacionCta = cta -> {
        cta.setFechaOperacion(Util.getCurrentDateAsString("dd/MM/yyyy"));
        return cta;
    };

}
