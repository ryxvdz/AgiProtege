package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.Dependente.RequestDTO.DependenteRequestDTO;
import com.AgiBank.AgiProtege.dto.Dependente.ResponseDTO.DependenteResponseDTO;
import com.AgiBank.AgiProtege.model.Dependente;
import com.AgiBank.AgiProtege.model.Vida;
import com.AgiBank.AgiProtege.repository.DependenteRepository;
import com.AgiBank.AgiProtege.repository.VidaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class DependenteService {


    private final DependenteRepository repository;
    private final VidaService vidaService;
    private final ClienteService clienteService;
    private final VidaRepository vidaRepository;


    public DependenteService(DependenteRepository repository, VidaService vidaService, ClienteService clienteService, VidaRepository vidaRepository) {
        this.repository = repository;
        this.vidaService = vidaService;
        this.clienteService = clienteService;
        this.vidaRepository = vidaRepository;

    }


    public DependenteResponseDTO adicionarDependente(DependenteRequestDTO dto) {
        //verifica se o seguro de vida existe
        Vida seguroVida = vidaRepository.findById(dto.seguroVida()).orElseThrow(
                () -> new RuntimeException("Seguro de vida não encontrado!"));

        Dependente dependente = new Dependente();
        dependente.setNome(dto.nome());
        dependente.setParentesco(dto.parentesco());
        dependente.setSeguroVida(seguroVida);


        Dependente dependeCastrado = repository.save(dependente);

        return toResponseDTO(dependeCastrado);
    }



    public DependenteResponseDTO toResponseDTO(Dependente dependente) {
        return new DependenteResponseDTO(

                dependente.getNome(),
                dependente.getParentesco()

        );
    }
    public void deletarDependente(UUID idDependente) {
        repository.deleteById(idDependente);

    }

}