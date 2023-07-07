package com.bootcamp.bank.creditos.infrastructure.repository;

import com.bootcamp.bank.creditos.infrastructure.repository.dao.CuentaDao;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CuentaRepository extends ReactiveMongoRepository<CuentaDao,String> {
    @Query("{'idCliente':?0}")
    Flux<CuentaDao> findByIdCliente(String idCliente);

    @Query("{'tipoCuenta':?0}")
    Flux<CuentaDao> findByTipoCuenta(String tipoCuenta);

}
