package com.bootcamp.bank.creditos.application;

import com.bootcamp.bank.creditos.application.util.Util;
import com.bootcamp.bank.creditos.infrastructure.client.ClientApiClientes;
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
@Slf4j
@RequiredArgsConstructor
public class CreditoUseCase {

    private final CreditoProductoRepository creditoProductoRepository;

    private final ClientApiClientes clientApiClientes;

    @Value("${tipo.credito.personal}")  String tipoCreditoPersonal;
    @Value("${tipo.credito.empresarial}")  String tipoCreditoEmpresarial;
    @Value("${tipo.credito.tarjeta.credito}") String tipoCreditoTarjeta;

    /**
     * Generacion de creditos
     * @param creditoProductoDao
     * @return
     */
    public Mono<CreditoProductoDao> save(CreditoProductoDao creditoProductoDao) {
        creditoProductoDao = creditoPorTipo.apply(creditoProductoDao);
        if (creditoProductoDao.getTipoCredito().equals(tipoCreditoPersonal)){
            log.info("credito personal - id cliente :"+creditoProductoDao.getIdCliente());

            CreditoProductoDao finalCreditoProductoDao = creditoProductoDao;
            return clientApiClientes.getClientes(creditoProductoDao.getIdCliente())
                        .switchIfEmpty(Mono.error(new Exception()))
                        .flatMap(c -> {
                            return creditoProductoRepository.findByIdCliente(finalCreditoProductoDao.getIdCliente())
                                  .next()
                                  .switchIfEmpty(creditoProductoRepository.save(finalCreditoProductoDao));

                        });

        } else {
            log.info("credito empresarial - id cliente :"+creditoProductoDao.getIdCliente());

            CreditoProductoDao finalCreditoProductoDao = creditoProductoDao;
            return clientApiClientes.getClientes(creditoProductoDao.getIdCliente())
                    .switchIfEmpty(Mono.error(new Exception())
                    ).flatMap(c->{
                        log.info("se encontro cliente "+c.getId()+" nombre:"+c.getNombre());
                        return creditoProductoRepository.save(finalCreditoProductoDao);
                    });
        }

    }

    /**
     * Permite buscar un credito por id
     * @param id
     * @return
     */
    public Mono<CreditoProductoDao> findById(String id) {
        return creditoProductoRepository.findById(id);
    }

    /**
     * Permite actualizar un credito producto
     * @param creditoProductoDao
     * @return
     */
    public Mono<CreditoProductoDao> update(CreditoProductoDao creditoProductoDao) {
        return creditoProductoRepository.save(creditoProductoDao);
    }

    /**
     * Permite eliminar un producto credito por id
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
            case "TJC" -> {
                cred.setNumeroCredito("TJC"+Integer.toString(randomNumber));
                cred.setNumeroTarjeta("TAR-"+Integer.toString(randomNumber));
                cred.setFechaCreacion (Util.getCurrentDateAsString("dd/MM/yyyy"));
                cred.setLineaCredito(25000.00); // linea de credito referencial
                cred.setEstado("ACT");
            }
            default -> {

            }
        }
        return cred;
    };
}
