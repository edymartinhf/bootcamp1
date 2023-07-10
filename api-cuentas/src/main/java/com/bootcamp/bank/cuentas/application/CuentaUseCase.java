package com.bootcamp.bank.cuentas.application;

import com.bootcamp.bank.cuentas.infrastructure.client.ClientApiClientes;
import com.bootcamp.bank.cuentas.infrastructure.repository.CuentaRepository;
import com.bootcamp.bank.cuentas.infrastructure.repository.dao.CuentaDao;
import com.bootcamp.bank.cuentas.infrastructure.rest.dto.Cliente;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class CuentaUseCase {

    private final CuentaRepository cuentaRepository;

    private static Environment environment;

    @Autowired
    @Qualifier("clientClientes")
    private static ClientApiClientes clientApiClientes;

    @Value("${tipo.cliente.personal}")
    private String TIPO_CLIENTE_PERSONAL;

    @Value("${tipo.cliente.empresarial}")
    private String TIPO_CLIENTE_EMPRESARIAL;

    @Value("${tipo.cuenta.ahorro}")
    private String TIPO_CUENTA_AHORRO;

    @Value("${tipo.cuenta.corriente}")
    private String TIPO_CUENTA_CORRIENTE;

    @Value("${tipo.cuenta.plazo.fijo}")
    private String TIPO_CUENTA_PLAZO_FIJO;

    /**
     * Permite registrar una cuenta
     *
     * -Un cliente personal (PER) solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo.
     *  si existe una cuenta previa no procedera a realizar la grabacion para tipo de cuenta PER
     *
     * -Un cliente empresarial (EMP) no puede tener una cuenta de ahorro o de plazo fijo, pero sí múltiples cuentas corrientes.
     *
     * @param cuentaDao
     * @return
     */


    public Mono<CuentaDao> save( CuentaDao cuentaDao) {

         return clientApiClientes.getClientes(cuentaDao.getIdCliente())
                .switchIfEmpty(Mono.just(new Cliente()))
                .flatMap(c -> {
                    log.info("cliente = "+c.toString());
                    if (c.getTipoCli().equals(TIPO_CLIENTE_PERSONAL)) {
                        log.info("cuenta  personal");
                        asignacionFlagsSegunTipoCuenta.apply(cuentaDao);

                        return cuentaRepository
                                .findByIdCliente(cuentaDao.getIdCliente())
                                .next()
                                .switchIfEmpty(cuentaRepository.save(cuentaDao));

                    } else {
                        log.info("cuenta  empresarial");
                        // cuenta empresarial admite solo CTE
                        if (cuentaDao.getTipoCuenta().equals("CTE")) {
                            asignacionFlagsSegunTipoCuenta.apply(cuentaDao);
                            return cuentaRepository
                                    .save(cuentaDao);

                        } else {
                            // AHO / PZF no aplican a clientes EMP
                            return Mono.just(new CuentaDao());
                        }
                    }
                });

    }

    /**
     * Permite obtener todas las cuentas
     * @return
     */
    public Flux<CuentaDao> findAll(){

        return cuentaRepository.findAll();
    }


    /**
     * Permite obtener cuenta por id
     * @param id
     * @return
     */
    public Mono<CuentaDao> findById(String id){
        return cuentaRepository.findById(id);
    }

    /**
     * Permite obtener las cuenta existentes por el id de cliente
     * @param idCliente
     * @return
     */
    public Flux<CuentaDao> findByIdCliente(String idCliente){

        return cuentaRepository.findByIdCliente(idCliente);
    }

    /**
     * Permite actualizar la cuenta
     * @param cuentaDao
     * @return
     */
    public Mono<CuentaDao> update( CuentaDao cuentaDao) {
        return cuentaRepository.save(cuentaDao);
    }

    /**
     * Permite eliminar una cuenta
     * @param id
     * @return
     */
    public Mono<Void> delete( String id) {
        return cuentaRepository.deleteById(id);
    }

    Function<CuentaDao,CuentaDao> asignacionFlagsSegunTipoCuenta = cta -> {

        int randomNumber = generateRandomNumber(1, 1000);
        switch (cta.getTipoCuenta()) {
            case "AHO" -> {
                cta.setFlgComisionMantenimiento(false);
                cta.setFlgLimiteMovMensual(true);
                cta.setNumMaximoMovimientos(20); // numero maximo limitado
                cta.setNumeroCuenta("AHO".concat(Integer.toString(randomNumber)));
                cta.setFechaCreacion (getCurrentDateAsString("dd/MM/yyyy"));
                cta.setEstado("ACT");

            }
            case "CTE" -> {
                cta.setFlgComisionMantenimiento(true);
                cta.setFlgLimiteMovMensual(false);
                cta.setNumeroCuenta("CTE"+Integer.toString(randomNumber));
                cta.setFechaCreacion (getCurrentDateAsString("dd/MM/yyyy"));
                cta.setNumMaximoMovimientos(999999); // ilimitado
                cta.setEstado("ACT");
            }
            case "PZF" -> {
                cta.setFlgComisionMantenimiento(false);
                cta.setNumMaximoMovimientos(1); // solo uno
                cta.setNumeroCuenta("PZF"+Integer.toString(randomNumber));
                cta.setFechaCreacion (getCurrentDateAsString("dd/MM/yyyy"));
                cta.setEstado("ACT");
            }
            default -> {

            }
        }
        return cta;
    };

    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static String getCurrentDateAsString(String format) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(formatter);
    }


}


