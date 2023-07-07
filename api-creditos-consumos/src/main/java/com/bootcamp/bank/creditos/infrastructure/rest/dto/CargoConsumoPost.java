package com.bootcamp.bank.creditos.infrastructure.rest.dto;


import lombok.Data;

@Data
public class CargoConsumoPost {
    private String idCliente;
    private String numeroCredito;
    private String fechaConsumo;
    private Double importe;
}
