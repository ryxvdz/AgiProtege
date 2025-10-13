package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.FipeDTO;
import com.AgiBank.AgiProtege.dto.MarcaFipeDTO;
import com.AgiBank.AgiProtege.dto.ModeloFipeDTO;
import com.AgiBank.AgiProtege.service.FipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fipe")
@AllArgsConstructor
public class FipeController {

    private final FipeService fipeService;

    @GetMapping("/marcas")
    public List<MarcaFipeDTO> listarMarcas() {
        return fipeService.listarMarcas();
    }

    @GetMapping("/modelos/{nomeMarca}")
    public List<ModeloFipeDTO> listarModelos(@PathVariable String nomeMarca) {
        return fipeService.listarModelos(nomeMarca);
    }

    @GetMapping("/anos/{nomeMarca}/{nomeModelo}")
    public List<Map<String, String>> listarAnos(@PathVariable String nomeMarca, @PathVariable String nomeModelo) {
        return fipeService.listarAnos(nomeMarca, nomeModelo);
    }

    @GetMapping("/detalhes")
    public FipeDTO buscarPorNome(@RequestParam String nomeMarca, String nomeModelo, String ano) {
        return fipeService.buscarPorNome(nomeMarca, nomeModelo, ano);
    }

}
