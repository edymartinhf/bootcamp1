package com.bootcamp.bank.operaciones.infrastructure.rest.dto;

import lombok.Data;

@Data
public class OperacionCta {
    private String id;
    private String idCliente;
    private String tipoOperacion; // DEP = deposito , RET = RETIRO
    private String fechaOperacion;
    private String numeroCuenta;
    private Double importe;
}
