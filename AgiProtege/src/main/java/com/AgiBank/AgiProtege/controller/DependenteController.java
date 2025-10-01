package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.DependenteRequestDTO;
import com.AgiBank.AgiProtege.dto.DependenteResponseDTO;
import com.AgiBank.AgiProtege.service.DependenteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dependente")
@AllArgsConstructor

public class DependenteController {

    private final DependenteService dependenteService;

    @PostMapping
    public DependenteResponseDTO adicionarDependente(@RequestBody DependenteRequestDTO dto){

        return dependenteService.adicionarDependente(dto);
    }
}
