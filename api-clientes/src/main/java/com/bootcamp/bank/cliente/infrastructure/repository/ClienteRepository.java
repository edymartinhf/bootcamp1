package com.bootcamp.bank.cliente.infrastructure.repository;

import com.bootcamp.bank.cliente.infrastructure.repository.dao.ClienteDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends ReactiveMongoRepository<ClienteDao,String> {

}
