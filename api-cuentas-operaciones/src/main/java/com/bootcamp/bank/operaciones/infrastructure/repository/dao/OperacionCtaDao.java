package com.bootcamp.bank.operaciones.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Data
@Document("operacionescuenta")
public class OperacionCtaDao {
    @Id
    private String id;
    private String idCliente;
    private String tipoOperacion; // DEP = deposito , RET = RETIRO
    private String fechaOperacion;
    private String numeroCuenta;
    private Double importe;

}
