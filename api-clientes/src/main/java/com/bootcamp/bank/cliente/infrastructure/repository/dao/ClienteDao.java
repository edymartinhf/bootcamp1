package com.bootcamp.bank.cliente.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("clientes")
public class ClienteDao {
    private String id;
    private String tipoCli;
    private String tipoDocumento;
    private String nroDocumento;
    private String nombre;
    private String razonSocial;
    private Double limiteCredito;
}
