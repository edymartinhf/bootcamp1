package com.bootcamp.bank.creditos.infrastructure.rest;

import com.bootcamp.bank.creditos.application.CreditoUseCase;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.CreditoProductoDao;
import com.bootcamp.bank.creditos.infrastructure.rest.dto.CreditoProducto;
import com.bootcamp.bank.creditos.infrastructure.rest.dto.CreditoProductoPost;
import com.bootcamp.bank.creditos.infrastructure.repository.CreditoProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller gestion de creditos
 */
@RestController
@RequestMapping("/creditos")
@RequiredArgsConstructor
public class CreditoResource {

    private final CreditoProductoRepository creditoProductoRepository;

    private final CreditoUseCase creditoUseCase;

    /**
     * Permite crear producto de credito
     * @param creditoProductoPost
     * @return
     */
    @PostMapping
    public Mono<CreditoProducto> createCreditProduct(@RequestBody CreditoProductoPost creditoProductoPost) {
        return creditoUseCase.save(this.fromCreditoProductoPostToCreditoProductoDao(creditoProductoPost))
                .map(this::fromCreditoProductoDaoToCreditoProductoDto);
    }

    /**
     * Permite Obtener todos los productos de credito
     * @return
     */
    @GetMapping
    public Flux<CreditoProducto> getAll(){
        return creditoProductoRepository.findAll().map(this::fromCreditoProductoDaoToCreditoProductoDto);
    }

    /**
     * Permite obtener productos de credito por id cliente
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Flux<CreditoProducto> findClienteByIdCliente(@PathVariable String id) {
        return creditoProductoRepository.findByIdCliente(id)
                .map(this::fromCreditoProductoDaoToCreditoProductoDto);
    }


    private CreditoProducto fromCreditoProductoDaoToCreditoProductoDto(CreditoProductoDao creditoProductoDao) {
        CreditoProducto creditoProducto = new CreditoProducto();
        BeanUtils.copyProperties(creditoProductoDao,creditoProducto);
        return creditoProducto;
    }

    private CreditoProductoDao fromCreditoProductoPostToCreditoProductoDao(CreditoProductoPost creditoProductoPost) {
        CreditoProductoDao creditoProductoDao = new CreditoProductoDao();
        BeanUtils.copyProperties(creditoProductoPost,creditoProductoDao);
        return creditoProductoDao;
    }
}

