package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.Dependente.RequestDTO.DependenteRequestDTO;
import com.AgiBank.AgiProtege.dto.Dependente.ResponseDTO.DependenteResponseDTO;
import com.AgiBank.AgiProtege.service.DependenteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dependente")
@AllArgsConstructor

public class DependenteController {

    private final DependenteService dependenteService;

    @PostMapping
    public DependenteResponseDTO adicionarDependente(@RequestBody DependenteRequestDTO dto){

        return dependenteService.adicionarDependente(dto);
    }
    @DeleteMapping
    public void deletar(@PathVariable UUID idDependente){
        dependenteService.deletarDependente(idDependente);
    }
}
