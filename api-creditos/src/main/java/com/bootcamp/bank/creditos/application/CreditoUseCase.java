package com.bootcamp.bank.creditos.application;

import com.bootcamp.bank.creditos.application.util.Util;
import com.bootcamp.bank.creditos.infrastructure.repository.CreditoProductoRepository;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.CreditoProductoDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Creditos Logica Negocio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreditoUseCase {

    private final CreditoProductoRepository creditoProductoRepository;

    @Value("${tipo.credito.personal}")
    private final String TIPO_CREDITO_PERSONAL;

    @Value("${tipo.credito.empresarial}")
    private final String TIPO_CREDITO_EMPRESARIAL;

    @Value("${tipo.credito.tarjeta.credito}")
    private final String TIPO_CREDITO_TARJETA;

    /**
     * Generacion de creditos
     * @param creditoProductoDao
     * @return
     */
    public Mono<CreditoProductoDao> save(CreditoProductoDao creditoProductoDao) {
        creditoProductoDao = creditoPorTipo.apply(creditoProductoDao);
        if (creditoProductoDao.getTipoCredito().equals(TIPO_CREDITO_PERSONAL)){
            log.info("credito personal - id cliente :"+creditoProductoDao.getIdCliente());
            return creditoProductoRepository.findByIdCliente(creditoProductoDao.getIdCliente())
                    .next()
                    .switchIfEmpty(creditoProductoRepository.save(creditoProductoDao));


        } else {
            log.info("credito empresarial - id cliente :"+creditoProductoDao.getIdCliente());
            return creditoProductoRepository.save(creditoProductoDao);
        }

    }

    /**
     * @param id
     * @return
     */
    public Mono<CreditoProductoDao> findById(String id) {
        return creditoProductoRepository.findById(id);
    }

    /**
     * @param creditoProductoDao
     * @return
     */
    public Mono<CreditoProductoDao> update(CreditoProductoDao creditoProductoDao) {
        return creditoProductoRepository.save(creditoProductoDao);
    }

    /**
     * @param id
     * @return
     */
    public Mono<Void> delete(String id){
        return creditoProductoRepository.deleteById(id);
    }

    Function<CreditoProductoDao,CreditoProductoDao> creditoPorTipo = cred -> {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        switch (cred.getTipoCredito()) {
            case "PER" -> {
                cred.setNumeroCredito("PER".concat(Integer.toString(randomNumber)));
                cred.setFechaCreacion (Util.getCurrentDateAsString("dd/MM/yyyy"));
                cred.setLineaCredito(10000.00); // linea de credito referencial
                cred.setEstado("ACT");
            }
            case "EMP" -> {
                cred.setNumeroCredito("EMP"+Integer.toString(randomNumber));
                cred.setFechaCreacion (Util.getCurrentDateAsString("dd/MM/yyyy"));
                cred.setLineaCredito(50000.00); // linea de credito referencial
                cred.setEstado("ACT");
            }
            default -> {

            }
        }
        return cred;
    };
}
