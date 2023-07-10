package com.bootcamp.bank.operaciones.infrastructure.rest;

import com.bootcamp.bank.operaciones.application.OperacionesCuentasUseCase;
import com.bootcamp.bank.operaciones.infrastructure.repository.OperacionesCuentaRepository;
import com.bootcamp.bank.operaciones.infrastructure.repository.dao.OperacionCtaDao;
import com.bootcamp.bank.operaciones.infrastructure.rest.dto.OperacionCta;
import com.bootcamp.bank.operaciones.infrastructure.rest.dto.OperacionCtaPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller Operaciones depositos y retiros de cuentas
 */
@RestController
@RequestMapping("/operaciones/cuentas")
@RequiredArgsConstructor
@Slf4j
public class OperacionesCuentaResource {

    private final OperacionesCuentaRepository operacionesCuentaRepository;

    private final OperacionesCuentasUseCase operacionesCuentasUseCase;

    /**
     * Permite registrar depositos y retiros
     * @param operationCtaPost
     * @return
     */
    @PostMapping("/save")
    public Mono<OperacionCta> save(@RequestBody OperacionCtaPost operationCtaPost){
        return operacionesCuentasUseCase.save(this.fromOperacionPostToOperacionDao(operationCtaPost)).
                map(this::fromOperacionClienteDaoToOperacionDto);
    }


    /**
     * Permite visualizar depositos y retiros
     * @return
     */
    @GetMapping
    public Flux<OperacionCta> findAll(){
        return operacionesCuentasUseCase.findAll().
                map(this::fromOperacionClienteDaoToOperacionDto);
    }

    /**
     * Permite obtener operaciones deposito y retiro por id cliente
     * @param idCliente
     * @return
     */
    @GetMapping("/cliente/{idCliente}")
    public Flux<OperacionCta> findOperacionesByIdCliente(@PathVariable(name = "idCliente") String idCliente) {
        return operacionesCuentaRepository.findByIdCliente(idCliente).map(this::fromOperacionClienteDaoToOperacionDto);

    }

    /**
     * Permite obtener operaciones por numero cuenta
     * @param numeroCuenta
     * @return
     */
    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public Flux<OperacionCta> findByNumeroCuenta(@PathVariable(name = "numeroCuenta") String numeroCuenta) {
        return operacionesCuentasUseCase.findByNumeroCuenta(numeroCuenta)
                .map(this::fromOperacionClienteDaoToOperacionDto);

    }

    /**
     * Permite Obtener tipos por numero de cuenta y tipo
     * @param numeroCuenta
     * @param tipoOperacion
     * @return
     */
    @GetMapping("/cuenta/{numeroCuenta}/tipo/{tipoOperacion}")
    public Flux<OperacionCta> findByNumeroCuentaTipo(
            @PathVariable(name = "numeroCuenta") String numeroCuenta,
            @PathVariable(name = "tipoOperacion") String tipoOperacion
    ) {
        log.info("peticion numeroCuenta:"+numeroCuenta+" tipoOperacion:"+tipoOperacion);
        return operacionesCuentasUseCase.findByNumeroCuentaAndTipoOperacion(numeroCuenta,tipoOperacion)
                .map(this::fromOperacionClienteDaoToOperacionDto);

    }


    private OperacionCta fromOperacionClienteDaoToOperacionDto(OperacionCtaDao operacionCtaDao) {
        OperacionCta operacionCta = new OperacionCta();
        BeanUtils.copyProperties(operacionCtaDao,operacionCta);
        return operacionCta;
    }

    private OperacionCtaDao fromOperacionPostToOperacionDao(OperacionCtaPost operacionCtaPost) {
        OperacionCtaDao operacionCtaDao = new OperacionCtaDao();
        BeanUtils.copyProperties(operacionCtaPost,operacionCtaDao);
        return operacionCtaDao;
    }

}