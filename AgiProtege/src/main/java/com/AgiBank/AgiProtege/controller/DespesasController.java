package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.DespesasRequestDTO;
import com.AgiBank.AgiProtege.dto.DespesasResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.service.DespesasService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/seguroDespesa")
@AllArgsConstructor

public class DespesasController {
    private final DespesasService despesasService;

    @PostMapping
    public DespesasResponseDTO criarSeguroDespesas(@RequestBody DespesasRequestDTO dto, @AuthenticationPrincipal Cliente cliente){
        UUID id = cliente.getIdCliente();
        return despesasService.criarSeguroDespesas(dto, id);
    }
}
