package com.bootcamp.bank.cliente.infrastructure.rest;

import com.bootcamp.bank.cliente.infrastructure.repository.ClienteRepository;
import com.bootcamp.bank.cliente.infrastructure.repository.dao.ClienteDao;
import com.bootcamp.bank.cliente.infrastructure.rest.dto.Cliente;
import com.bootcamp.bank.cliente.infrastructure.rest.dto.ClientePost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Cliente Permite el manejo de la informacion de clientes
 */
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteRepository clienteRepository;

    /**
     * Permite obtener todos los clientes
     * @return
     */
    @GetMapping
    public Flux<Cliente> getAll() {
        return clienteRepository.findAll()
                .map(this::fromClienteDaoToClienteDto);
    }



    /**
     * Permite Obtener clientes por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Cliente> findClienteById(@PathVariable String id) {
        return clienteRepository.findById(id)
                .map(this::fromClienteDaoToClienteDto);
    }

    /**
     * Permite Registrar clientes
     * @param cliente
     * @return
     */
    @PostMapping
    public Mono<Cliente> createCliente(@RequestBody ClientePost cliente) {
        return clienteRepository.save(this.fromClientePostToClienteDao(cliente))
                .map(this::fromClienteDaoToClienteDto);
    }

    /**
     * @param cliente
     * @return
     */
    @PutMapping
    public Mono<Cliente> modifyCliente(@RequestBody ClientePost cliente,@PathVariable String id) {
        return clienteRepository.findById(id)
                .flatMap(c-> {
                    return clienteRepository.save(fromClientePostToClienteDao(cliente))
                            .map(e-> fromClienteDaoToClienteDto(e));
                });
    }

    /**
     * Permite eliminar clientes
     * @param id
     */
    @DeleteMapping
    public void deleteCliente(@PathVariable String id) {
        clienteRepository.deleteById(id);
    }

    private Cliente fromClienteDaoToClienteDto(ClienteDao clienteDao) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDao,cliente);
        return cliente;
    }

    private ClienteDao fromClientePostToClienteDao(ClientePost clientePost) {
        ClienteDao cliente = new ClienteDao();
        BeanUtils.copyProperties(clientePost,cliente);
        return cliente;
    }
}
