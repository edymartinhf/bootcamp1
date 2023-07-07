package com.bootcamp.bank.saldos.application;

import com.bootcamp.bank.saldos.infrastructure.client.*;
import com.bootcamp.bank.saldos.infrastructure.rest.SaldosResource;
import com.bootcamp.bank.saldos.infrastructure.rest.dto.SaldoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<SaldoResponse> getSaldos(String id){

        clientApiConsumos.getConsumosNumeroCredito("").reduce(0.00,(acum,e)->acum+e.getImporte());
        clientApiPagos.getPagosNumeroCredito("").reduce(0.00,(acum,e)->acum+e.getImporte());
        clientApiOperaciones.getOperacionesPorNumeroCuenta("").reduce(0.00, (acum,e)->acum+e.getImporte())

        return clientApiClientes.getClientes(id).map(c->{
            SaldoResponse saldo =new SaldoResponse();
            saldo.setIdCliente(id);
            saldo.setCliente(c);
            return saldo;
        }).flatMap(c->{
            return clientApiCreditos.getCreditos(id)
                    .collectList()
                    .map(l->{
                        SaldoResponse saldo =new SaldoResponse();
                        saldo.setCliente(c.getCliente());
                        saldo.setIdCliente(id);
                        saldo.setCreditos(l);
                        return saldo;
                    });
        }).flatMap(c->{
            return clientApiCuentas.getCuentas(id)
                    .collectList()
                    .map(l->{
                        SaldoResponse saldo =new SaldoResponse();
                        saldo.setCliente(c.getCliente());
                        saldo.setCreditos(c.getCreditos());
                        saldo.setIdCliente(id);
                        saldo.setCuentas(l);
                        return saldo;
                    });
        });
    }


}
