package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.client.FipeClient;
import com.AgiBank.AgiProtege.dto.FipeDTO;
import com.AgiBank.AgiProtege.dto.MarcaFipeDTO;
import com.AgiBank.AgiProtege.dto.ModeloFipeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FipeService {

    private final FipeClient fipeClient;

    public FipeService(FipeClient fipeClient) {
        this.fipeClient = fipeClient;
    }

    public List<MarcaFipeDTO> listarMarcas() {
        return fipeClient.listarMarcas();
    }

    public List<ModeloFipeDTO> listarModelos(String nomeMarca) {
        MarcaFipeDTO marca = fipeClient.listarMarcas().stream()
                .filter(m -> m.nome().equalsIgnoreCase(nomeMarca))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Marca não encontrada: " + nomeMarca));
        return fipeClient.listarModelosPorMarca(marca.codigo().toString());
    }

    public List<Map<String, String>> listarAnos(String nomeMarca, String nomeModelo) {
        MarcaFipeDTO marca = fipeClient.listarMarcas().stream()
                .filter(m -> m.nome().equalsIgnoreCase(nomeMarca.trim()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Marca não encontrada: " + nomeModelo));

        ModeloFipeDTO modelo = fipeClient.listarModelosPorMarca(marca.codigo().toString()).stream()
                .filter(m -> m.modelo().equalsIgnoreCase(nomeModelo.trim()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado: " + nomeModelo));

        return fipeClient.listarAnosPorModelos(marca.codigo().toString(), String.valueOf(modelo.codigo()));
    }

    public FipeDTO buscarPorNome(String nomeMarca, String nomeModelo, String ano) {
        System.out.println("Marca: "+nomeMarca);
        System.out.println("Modelo: "+nomeModelo);
        System.out.println("Ano: "+ano);
        return fipeClient.buscarPorNome(nomeMarca, nomeModelo, ano);
    }

}
