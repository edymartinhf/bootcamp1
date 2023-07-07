package com.bootcamp.bank.movimientos.infrastructure.rest;

import com.bootcamp.bank.movimientos.application.MovimientosUseCase;
import com.bootcamp.bank.movimientos.infrastructure.rest.dto.Movimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientosResource {

    private final MovimientosUseCase movimientosUseCase;
    @GetMapping("/{id}")
    public Mono<Movimiento> getMovimientos(@PathVariable String id){
        return movimientosUseCase.getMovimientos(id);
    }
}
