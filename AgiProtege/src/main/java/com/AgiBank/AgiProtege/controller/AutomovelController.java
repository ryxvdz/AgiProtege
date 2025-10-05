package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.Automovel.RequestDTO.AutomovelRequestDTO;
import com.AgiBank.AgiProtege.dto.Automovel.ResponseDTO.AutomovelResponseDTO;
import com.AgiBank.AgiProtege.service.AutomovelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/SeguroAutomovel")
@AllArgsConstructor
public class AutomovelController {
    private final AutomovelService service;

    @PostMapping
    public AutomovelResponseDTO criarSeguroAutomovel(@RequestBody AutomovelRequestDTO dto) {
        return service.criarSeguroAutomovel(dto);
    }

    @DeleteMapping
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
