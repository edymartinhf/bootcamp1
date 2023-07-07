package com.bootcamp.bank.creditos.infrastructure.repository.dao;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("pagos")
public class PagoDao {
    @Id
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String fechaPago;
    private Double importe;

}