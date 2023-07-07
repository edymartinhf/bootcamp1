package com.bootcamp.bank.creditos.infrastructure.rest;

import com.bootcamp.bank.creditos.application.CreditoConsumoUseCase;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.CargoConsumoDao;
import com.bootcamp.bank.creditos.infrastructure.repository.dao.CreditoConsumosRepository;
import com.bootcamp.bank.creditos.infrastructure.rest.dto.CargoConsumo;
import com.bootcamp.bank.creditos.infrastructure.rest.dto.CargoConsumoPost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller Consumo Credito
 */
@RestController
@RequestMapping("/creditos/tarjetas/cargos")
@RequiredArgsConstructor
public class CreditoConsumoResource {

    private final CreditoConsumosRepository creditoConsumosRepository;

    private final CreditoConsumoUseCase creditoConsumoUseCase;

    /**
     * Permite cargar consumos a sus tarjetas de crédito
     * @param cargoConsumo
     * @return
     */
    @PostMapping
    public Mono<CargoConsumo> save(@RequestBody CargoConsumoPost cargoConsumo){

        return creditoConsumoUseCase.save(fromCargoConsumoPostToCargoConsumoDao(cargoConsumo))
                .map(this::fromCargoConsumoDaoToCargoConsumoDto);
    }


    /**
     * Permite obtener consumos por cliente
     * @param idCliente
     * @return
     */
    @GetMapping("/{id}")
    public Flux<CargoConsumo> findConsumosByIdCliente(@PathVariable(name = "id") String idCliente) {
        return creditoConsumosRepository.findByIdCliente(idCliente).map(this::fromCargoConsumoDaoToCargoConsumoDto);

    }

    /**
     * Permite obtener consumos por numero de credito
     * @param numeroCredito
     * @return
     */
    @GetMapping("/numero-credito/{numeroCredito}")
    public Flux<CargoConsumo> findPagosByNumeroCredito(@PathVariable(name = "numeroCredito") String numeroCredito) {
        return creditoConsumosRepository.findByNumeroCredito(numeroCredito)
                .map(this::fromCargoConsumoDaoToCargoConsumoDto);

    }

    private CargoConsumo fromCargoConsumoDaoToCargoConsumoDto(CargoConsumoDao cargoConsumoDao) {
        CargoConsumo cargoConsumo = new CargoConsumo();
        BeanUtils.copyProperties(cargoConsumoDao, cargoConsumo);
        return cargoConsumo;
    }

    private CargoConsumoDao fromCargoConsumoPostToCargoConsumoDao(CargoConsumoPost cargoCargaConsumoPost) {
        CargoConsumoDao cargaConsumoDao = new CargoConsumoDao();
        BeanUtils.copyProperties(cargoCargaConsumoPost,cargaConsumoDao);
        return cargaConsumoDao;
    }
}
