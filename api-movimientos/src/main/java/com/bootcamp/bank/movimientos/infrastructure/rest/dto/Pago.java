package com.bootcamp.bank.movimientos.infrastructure.rest.dto;

import lombok.Data;

@Data
public class Pago {
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String fechaPago;
    private Double importe;
}
