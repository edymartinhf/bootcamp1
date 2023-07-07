package com.bootcamp.bank.creditos.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cargosconsumo")
public class CargoConsumoDao {
    @Id
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String fechaConsumo;
    private Double importe;
}
