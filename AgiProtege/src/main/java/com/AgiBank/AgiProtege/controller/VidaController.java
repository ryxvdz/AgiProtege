package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.VidaRequestDTO;
import com.AgiBank.AgiProtege.dto.VidaResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.service.VidaService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/seguroVida")
@AllArgsConstructor
public class VidaController {

    private final VidaService vidaService;

    @PostMapping
    public VidaResponseDTO criarSeguroVida(@RequestBody VidaRequestDTO dto, @AuthenticationPrincipal Cliente cliente) {
        UUID id = cliente.getIdCliente();
        return vidaService.criarSeguroVida(dto, id);
    }

}
