package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.SinistroRequestDTO;
import com.AgiBank.AgiProtege.dto.SinistroResponseDTO;
import com.AgiBank.AgiProtege.service.SinistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sinistros")
public class SinistroController {

    @Autowired
    private SinistroService sinistroService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<SinistroResponseDTO> criarSinistro(
            @RequestParam("descricao") String descricao,
            @RequestParam("idApolice") UUID idApolice,
            @RequestParam("documento") MultipartFile documento) {
        SinistroRequestDTO request = new SinistroRequestDTO(descricao, idApolice, documento);
        SinistroResponseDTO response = sinistroService.criarSinistro(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SinistroResponseDTO>> listarSinistros() {
        List<SinistroResponseDTO> lista = sinistroService.listarSinistros();
        return ResponseEntity.ok(lista);
    }
}

