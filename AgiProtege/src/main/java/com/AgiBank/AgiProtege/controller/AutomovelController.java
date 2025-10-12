package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.AutomovelRequestDTO;
import com.AgiBank.AgiProtege.dto.AutomovelResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.service.AutomovelService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/SeguroAutomovel")
@AllArgsConstructor
public class AutomovelController {
    private final AutomovelService service;

    @PostMapping
    public AutomovelResponseDTO criarSeguroAutomovel(@RequestBody AutomovelRequestDTO dto, @AuthenticationPrincipal Cliente cliente) {
        UUID id = cliente.getIdCliente();
        return service.criarSeguroAutomovel(dto, id);
    }
}
