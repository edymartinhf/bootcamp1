package com.bootcamp.bank.creditos.infrastructure.rest;

import com.bootcamp.bank.creditos.application.CreditosPagosUseCase;
import com.bootcamp.bank.creditos.infrastructure.repository.CreditoProductoPagoRepository;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.PagoDao;
import com.bootcamp.bank.creditos.infrastructure.rest.dto.Pago;
import com.bootcamp.bank.creditos.infrastructure.rest.dto.PagoPost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller Pagos de credito producto
 */
@RestController
@RequestMapping("/creditos/pago")
@RequiredArgsConstructor
public class CreditoProductoPagosResource {

    private final CreditoProductoPagoRepository creditoProductoPagoRepository;

    private final CreditosPagosUseCase creditosPagosUseCase;

    /**
     * Permite realizar pagar productos de credito
     * @param pagoPost
     * @return
     */
    @PostMapping
    public Mono<Pago> save(@RequestBody PagoPost pagoPost){
        return creditosPagosUseCase.save(this.fromPagoPostToPagoDao(pagoPost))
                .map(this::fromPagoDaoToPagoDto);
    }


    /**
     * Permite Obtener pagos de credito por cliente
     * @param idCliente
     * @return
     */
    @GetMapping("/{id}")
    public Flux<Pago> findPagosByIdCliente(@PathVariable(name = "id") String idCliente) {
        return creditoProductoPagoRepository.findByIdCliente(idCliente)
                .map(this::fromPagoDaoToPagoDto);

    }

    /**
     * Permite consultar pagos por numero de credito
     * @param numeroCredito
     * @return
     */
    @GetMapping("/numero-credito/{numeroCredito}")
    public Flux<Pago> findPagosByNumeroCredito(@PathVariable(name = "numeroCredito") String numeroCredito) {
        return creditoProductoPagoRepository.findByNumeroCredito(numeroCredito)
                .map(this::fromPagoDaoToPagoDto);

    }

    private Pago fromPagoDaoToPagoDto(PagoDao pagoDao) {
        Pago pago = new Pago();
        BeanUtils.copyProperties(pagoDao,pago);
        return pago;
    }

    private PagoDao fromPagoPostToPagoDao(PagoPost pagoPost) {
        PagoDao pagoDao = new PagoDao();
        BeanUtils.copyProperties(pagoPost,pagoDao);
        return pagoDao;
    }

}
