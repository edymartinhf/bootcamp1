package com.bootcamp.bank.creditos.infrastructure.rest.dto;

import lombok.Data;
import org.springframework.lang.NonNull;


@Data
public class Cliente {
    private String id;
    private String tipoCli;
    private String tipoDocumento;
    private String nroDocumento;
    private String nombre;
    private String razonSocial;
    private Double limiteCredito;

}
