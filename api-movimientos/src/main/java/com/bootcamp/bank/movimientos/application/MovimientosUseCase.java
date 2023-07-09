package com.bootcamp.bank.movimientos.application;

import com.bootcamp.bank.movimientos.infrastructure.client.ClientApiConsumos;
import com.bootcamp.bank.movimientos.infrastructure.client.ClientApiOperaciones;
import com.bootcamp.bank.movimientos.infrastructure.client.ClientApiPagos;
import com.bootcamp.bank.movimientos.infrastructure.rest.dto.Movimiento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovimientosUseCase {

    private final ClientApiConsumos clientApiConsumos;

    private final ClientApiPagos clientApiPagos;

    private final ClientApiOperaciones clientApiOperaciones;

    public Mono<Movimiento> getMovimientos(String id){

        return clientApiPagos.getPagos(id)
                .collectList()
                .map(l-> {
                    Movimiento mov=new Movimiento();
                    mov.setPagosTarjetaCredito(l);
                    return mov;
                }).flatMap(n -> {
                    return clientApiConsumos.getConsumos(id)
                            .collectList()
                            .map(e->{
                                Movimiento mov=new Movimiento();
                                mov.setPagosTarjetaCredito(n.getPagosTarjetaCredito());
                                mov.setCargosConsumoTarjetaCredito(e);
                                return mov;
                            });
                }).flatMap(c->{
                    return clientApiOperaciones.getOperaciones(id)
                            .collectList()
                            .map(e->{
                                Movimiento mov=new Movimiento();
                                mov.setIdCliente(id);
                                mov.setPagosTarjetaCredito(c.getPagosTarjetaCredito());
                                mov.setCargosConsumoTarjetaCredito(c.getCargosConsumoTarjetaCredito());
                                mov.setOperacionesCuentasCorriente(e);
                                return mov;
                            });
                });


    }
}
