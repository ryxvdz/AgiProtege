package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.ApoliceResponseDTO;
import com.AgiBank.AgiProtege.service.ApoliceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public void deletarApolicePorId(UUID id) {
        service.deletarApolicePorId(id);
    }
}
