package com.bootcamp.bank.cuentas.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase Entidad Cuenta Dao
 */
@Data
@Document("cuentas")
public class CuentaDao {
    @Id
    private String  id;
    private String  idCliente;
    private String  numeroCuenta;
    private String  fechaCreacion;
    private String  estado;
    private String  tipoCuenta; // AHO: ahorro  , CTE : cuenta corriente , PZF: plazo fijo
    private Boolean flgComisionMantenimiento;
    private Boolean flgLimiteMovMensual;
    private Integer numMaximoMovimientos;


}