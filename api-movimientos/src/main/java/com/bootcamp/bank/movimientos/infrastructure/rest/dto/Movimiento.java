package com.bootcamp.bank.movimientos.infrastructure.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class Movimiento {
    private String idCliente;
    private List<Pago> pagosTarjetaCredito;
    private List<OperacionCta> operacionesCuentasCorriente;
    private List<CargoConsumo> cargosConsumoTarjetaCredito;
}
