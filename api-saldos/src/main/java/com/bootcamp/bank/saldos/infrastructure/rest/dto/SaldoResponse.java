package com.bootcamp.bank.saldos.infrastructure.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaldoResponse {
    private String idCliente;
    private Cliente cliente;
    private List<CreditoProducto> creditos;
    private List<Cuenta> cuentas;
}
