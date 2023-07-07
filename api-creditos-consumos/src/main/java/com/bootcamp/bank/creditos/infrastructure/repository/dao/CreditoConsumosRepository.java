package com.bootcamp.bank.creditos.infrastructure.repository.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditoConsumosRepository extends ReactiveMongoRepository<CargoConsumoDao,String> {

    @Query("{'idCliente':?0}")
    Flux<CargoConsumoDao> findByIdCliente(String idCliente);
    @Query("{'numeroCredito':?0}")
    Flux<CargoConsumoDao> findByNumeroCredito(String numeroCredito);
}
