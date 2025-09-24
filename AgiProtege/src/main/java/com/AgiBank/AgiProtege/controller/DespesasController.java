package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.DespesasRequestDTO;
import com.AgiBank.AgiProtege.dto.DespesasResponseDTO;
import com.AgiBank.AgiProtege.service.DespesasService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seguroDespesa")
@AllArgsConstructor

public class DespesasController {
    private final DespesasService despesasService;

    @PostMapping
    public DespesasResponseDTO criarSeguroDespesas(@RequestBody DespesasRequestDTO dto){
        return despesasService.criarSeguroDespesas(dto);
    }
}
