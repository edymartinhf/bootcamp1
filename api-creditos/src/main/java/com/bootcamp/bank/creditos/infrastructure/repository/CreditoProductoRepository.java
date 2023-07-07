package com.bootcamp.bank.creditos.infrastructure.repository;

import com.bootcamp.bank.creditos.infrastructure.repository.dao.CreditoProductoDao;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditoProductoRepository extends ReactiveMongoRepository<CreditoProductoDao,String> {
    @Query("{'idCliente':?0}")
    Flux<CreditoProductoDao> findByIdCliente(String idCliente);
}
