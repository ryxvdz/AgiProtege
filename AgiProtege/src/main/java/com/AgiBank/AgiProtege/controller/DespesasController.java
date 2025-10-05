package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.Despesas.RequestDTO.DespesasRequestDTO;
import com.AgiBank.AgiProtege.dto.Despesas.ResponseDTO.DespesasResponseDTO;
import com.AgiBank.AgiProtege.service.DespesasService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/seguroDespesa")
@AllArgsConstructor

public class DespesasController {
    private final DespesasService despesasService;

    @PostMapping
    public DespesasResponseDTO criarSeguroDespesas(@RequestBody DespesasRequestDTO dto){
        return despesasService.criarSeguroDespesas(dto);
    }

    @DeleteMapping
    public void deletar(@PathVariable UUID id) {
        despesasService.deletar(id);
    }
}
