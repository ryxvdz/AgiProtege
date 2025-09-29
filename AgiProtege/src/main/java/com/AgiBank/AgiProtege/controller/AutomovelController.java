package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.AutomovelRequestDTO;
import com.AgiBank.AgiProtege.dto.AutomovelResponseDTO;
import com.AgiBank.AgiProtege.service.AutomovelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SeguroAutomovel")
@AllArgsConstructor
public class AutomovelController {
    private final AutomovelService service;

    @PostMapping
    public AutomovelResponseDTO criarSeguroAutomovel(@RequestBody AutomovelRequestDTO dto) {
        return service.criarSeguroAutomovel(dto);
    }
}
