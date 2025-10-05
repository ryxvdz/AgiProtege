package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.Sinistro.RequestDTO.SinistroRequestDTO;
import com.AgiBank.AgiProtege.dto.Sinistro.ResponseDTO.SinistroResponseDTO;
import com.AgiBank.AgiProtege.service.SinistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sinistros")
public class SinistroController {
    @Autowired
    private SinistroService sinistroService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<SinistroResponseDTO> registrarSinistro(
            @RequestParam("descricao") String descricao,
            @RequestParam("idApolice") UUID idApolice,
            @RequestParam(value = "documento", required = false) MultipartFile documento) {
        try {
            SinistroRequestDTO request = new SinistroRequestDTO(descricao, idApolice);
            SinistroResponseDTO resp = sinistroService.registrarSinistro(request, documento);
            return ResponseEntity.ok(resp);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new SinistroResponseDTO(null, "Erro", "Falha ao salvar documento: " + e.getMessage()));
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SinistroResponseDTO>> listarPorCliente(@PathVariable UUID idCliente) {
        List<SinistroResponseDTO> sinistros = sinistroService.listarSinistrosPorCliente(idCliente);
        return ResponseEntity.ok(sinistros);
    }
}
