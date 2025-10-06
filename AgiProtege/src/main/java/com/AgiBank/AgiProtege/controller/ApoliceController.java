package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.ApoliceResponseDTO;
import com.AgiBank.AgiProtege.service.ApoliceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apolice")
public class ApoliceController {

    private final ApoliceService service;

    public ApoliceController(ApoliceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ApoliceResponseDTO buscarApolicePorId(UUID id) {
        return service.buscarApolicePorId(id);
    }

    @GetMapping("/apolicesUsuario/{cpf}")
    public List<ApoliceResponseDTO> buscarApolicesPorCpf(@PathVariable String cpf) {
        return service.buscarApolicesPorCpf(cpf);
    }

    @PatchMapping("/{id}/inativarApolice")
    public void inativarApolice(UUID id) {
        service.inativarApolicePorId(id);
    }
}
