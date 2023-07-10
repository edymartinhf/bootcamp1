package com.bootcamp.bank.saldos.application;

import com.bootcamp.bank.saldos.infrastructure.client.*;
import com.bootcamp.bank.saldos.infrastructure.rest.SaldosResource;
import com.bootcamp.bank.saldos.infrastructure.rest.dto.SaldoResponse;
import com.bootcamp.bank.saldos.infrastructure.rest.dto.Totales;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Casos de uso Saldos
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SaldosUseCase {

    private final ClientApiClientes clientApiClientes;

    private final ClientApiCreditos clientApiCreditos;

    private final ClientApiCuentas clientApiCuentas;

    private final ClientApiConsumos clientApiConsumos;

    private final ClientApiPagos clientApiPagos;

    private final ClientApiOperaciones clientApiOperaciones;

    /**
     * Permite obtener saldos por id cliente
     * @param id
     * @return
     */
    public Mono<SaldoResponse> getSaldos(String id){


        return clientApiClientes.getClientes(id).map(c-> {
            SaldoResponse saldo = new SaldoResponse();
            saldo.setIdCliente(id);
            saldo.setCliente(c);
            return saldo;
        }).flatMap(c->{
            return clientApiCreditos.getCreditos(id)
                    .flatMap(cred->{
                        log.info("numero credito "+cred.getNumeroCredito());
                        return Mono.zip(getConsumnos(cred.getNumeroCredito()), getPagos(cred.getNumeroCredito()), (consumos,pagos)->{
                            cred.setPagos(pagos);
                            cred.setConsumos(consumos);
                            cred.setSaldo(cred.getLineaCredito()+(cred.getConsumos()*-1)+cred.getPagos());
                            return  cred;
                        });

                    })
                    .collectList()
                    .map(cre->{
                        SaldoResponse saldo =new SaldoResponse();
                        saldo.setCliente(c.getCliente());
                        saldo.setIdCliente(id);
                        saldo.setCreditos(cre);
                        return saldo;
                    });
        }).flatMap(c->{
            return clientApiCuentas.getCuentas(id)
                    .flatMap(cue->{
                        log.info("numero cuenta "+cue.getNumeroCuenta());
                        return Mono.zip(getOperacionesPorTipo(cue.getNumeroCuenta(),"DEP"), getOperacionesPorTipo(cue.getNumeroCuenta(),"RET"), (deposito,retiro)->{
                            cue.setAbonos(deposito);
                            cue.setCargos(retiro);
                            cue.setSaldo(cue.getAbonos()+(cue.getCargos()*-1));
                            return  cue;
                        });
                    })
                    .collectList()
                    .map(cta->{
                        SaldoResponse saldo =new SaldoResponse();
                        saldo.setCliente(c.getCliente());
                        saldo.setCreditos(c.getCreditos());
                        saldo.setIdCliente(id);
                        saldo.setCuentas(cta);
                        return saldo;
                    });
        });
    }


    /**
     * Permite calcular los consumos por numero de credito
     * @param numeroCredito
     * @return
     */
    public Mono<Double> getConsumnos(String numeroCredito) {
        return clientApiConsumos
                .getConsumosNumeroCredito(numeroCredito)
                .reduce(0.00,(acum,e)->acum+e.getImporte());
    }

    /**
     * Permite obtener pagos por numero de credito
     * @param numeroCredito
     * @return
     */
    public Mono<Double> getPagos(String numeroCredito) {
        return clientApiPagos
                .getPagosNumeroCredito(numeroCredito)
                .reduce(0.00,(acum,e)->acum+e.getImporte());
    }

    /**
     * Permite Obtener las operaciones por numero de cuenta y por tipo
     * @param numeroCuenta
     * @param tipo
     * @return
     */
    public Mono<Double> getOperacionesPorTipo(String numeroCuenta,String tipo) {
        return clientApiOperaciones
                .getOperacionesPorNumeroCuenta(numeroCuenta,tipo)
                .reduce(0.00, (acum,e)->acum+e.getImporte());
    }

}
