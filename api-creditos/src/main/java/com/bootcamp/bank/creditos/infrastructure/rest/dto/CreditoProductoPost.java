package com.bootcamp.bank.creditos.infrastructure.rest.dto;

import lombok.Data;

@Data
public class CreditoProductoPost {
    private String  idCliente;
    private String  tipoCredito; // PER = personal , EMP = empresarial , TJC = tarjeta de credito
    private String  numeroCredito;
    private String  numeroTarjeta;
    private String  fechaCreacion;
    private Double  lineaCredito;


}
