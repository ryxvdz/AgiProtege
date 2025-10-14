package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.ClienteResponseDTO;
import com.AgiBank.AgiProtege.dto.EnderecoResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.Endereco;
import com.AgiBank.AgiProtege.service.ClienteService;
import com.AgiBank.AgiProtege.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final EnderecoService enderecoService;

    @PostMapping
    public ClienteResponseDTO cadastrarCliente(@RequestBody ClienteRequestDTO dto) {
        return clienteService.cadastrarCliente(dto);
    }

    @GetMapping("/me")
    public ClienteResponseDTO buscarClientePorId(@AuthenticationPrincipal Cliente cliente) {
        UUID id = cliente.getIdCliente();
        return clienteService.buscarClientePorId(id);
    }

    @PutMapping("/atualizar")
    public ClienteResponseDTO atualizarClientePorId(@AuthenticationPrincipal Cliente cliente, @RequestBody ClienteRequestDTO dto) {
        UUID id = cliente.getIdCliente();
        clienteService.atualizarClientePorId(id, dto);
        clienteService.calcularPerfilDeRiscoInical(id, dto);
        return clienteService.buscarClientePorId(id);
    }

    @PatchMapping("/inativarCliente")
    public void inativarCliente(@AuthenticationPrincipal Cliente cliente) {
        UUID id = cliente.getIdCliente();
        clienteService.inativarClientePorId(id);
    }

//    @PostMapping("/{id}/endereco")
//    public EnderecoResponseDTO adicionarEnderecoAoCliente(
//            @PathVariable UUID id,
//            @RequestParam String cep,
//            @RequestParam String numero) {
//        return enderecoService.adicionarEnderecoAoCliente(id, cep, numero);
//    }

    @GetMapping("/endereco/{id}")
    public EnderecoResponseDTO buscarEnderecoporId(@PathVariable UUID id) {
        return enderecoService.buscarEnderecoporId(id);
    }

}
