package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        clienteService.cadastrarCliente(cliente);
        return cliente;
    }

    @DeleteMapping("/{id}")
    public void deletarClientePorId(@PathVariable Integer id) {
        clienteService.deletarClientePorId(id);
    }
}
