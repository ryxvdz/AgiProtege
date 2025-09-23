package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.ClienteResponseDTO;
import com.AgiBank.AgiProtege.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ClienteResponseDTO cadastrarCliente(@RequestBody ClienteRequestDTO dto) {
        return clienteService.cadastrarCliente(dto);
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizarClientePorId(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        clienteService.calcularPerfilDeRiscoInical(id);
        return clienteService.atualizarClientePorId(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarClientePorId(@PathVariable Long id) {
        clienteService.deletarClientePorId(id);
    }
}
