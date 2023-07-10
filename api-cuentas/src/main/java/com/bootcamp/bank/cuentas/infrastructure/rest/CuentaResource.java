package com.bootcamp.bank.cuentas.infrastructure.rest;

import com.bootcamp.bank.cuentas.infrastructure.repository.dao.CuentaDao;
import com.bootcamp.bank.cuentas.infrastructure.rest.dto.Cuenta;
import com.bootcamp.bank.cuentas.infrastructure.rest.dto.CuentaPost;
import com.bootcamp.bank.cuentas.application.CuentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase controller gestion creacion de cuentas de ahorro - corriente - plazo fijo
 */
@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaResource {

    private final CuentaUseCase cuentaUseCase;

    /**
     * Permite crear una cuenta bancaria
     * @param cuenta
     * @return
     */
    @PostMapping
    public Mono<Cuenta> createAccount(@RequestBody CuentaPost cuenta) {
        return cuentaUseCase.save(this.fromCuentaPostToCuentaDao(cuenta))
                .map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite Obtener todas las cuentas bancarias
     * @return
     */
    @GetMapping
    public Flux<Cuenta> getAll(){

        return cuentaUseCase.findAll().map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite obtener credito por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Cuenta> findById(@PathVariable String id){
        return cuentaUseCase.findById(id).map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite obtener listado de cuentas bancarias por id cliente
     * @param idCliente
     * @return
     */
    @GetMapping("/cliente/{idCliente}")
    public Flux<Cuenta> findByIdCliente(@PathVariable String idCliente){

        return cuentaUseCase.findByIdCliente(idCliente).map(this::fromCuentaDaoToCuentaDto);
    }

    /**
     * Permite actualizar cuenta
     * @param cuenta
     * @return
     */
    @PutMapping
    public Mono<Cuenta> updateAccount(@RequestBody CuentaPost cuenta) {
        return cuentaUseCase.update(this.fromCuentaPostToCuentaDao(cuenta))
                .map(this::fromCuentaDaoToCuentaDto);
    }


    @DeleteMapping
    public Mono<Void> deleteAccount(@PathVariable String id) {
        return cuentaUseCase.delete(id);

    }

    private Cuenta fromCuentaDaoToCuentaDto(CuentaDao cuentaDao) {
        Cuenta cuenta = new Cuenta();
        BeanUtils.copyProperties(cuentaDao,cuenta);
        return cuenta;
    }

    private CuentaDao fromCuentaPostToCuentaDao(CuentaPost cuentaPost) {
        CuentaDao cuenta = new CuentaDao();
        BeanUtils.copyProperties(cuentaPost,cuenta);
        return cuenta;
    }

}
