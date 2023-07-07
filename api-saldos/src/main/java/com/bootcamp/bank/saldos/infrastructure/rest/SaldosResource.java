package com.bootcamp.bank.saldos.infrastructure.rest;

import com.bootcamp.bank.saldos.application.SaldosUseCase;
import com.bootcamp.bank.saldos.infrastructure.rest.dto.SaldoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/saldos")
@RequiredArgsConstructor
public class SaldosResource {

    private final SaldosUseCase saldosUseCase;

    @GetMapping("/{id}")
    public Mono<SaldoResponse> getSaldos(@PathVariable String id){
        return saldosUseCase.getSaldos(id);
    }
}
