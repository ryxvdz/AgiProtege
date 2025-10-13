package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.DependenteRequestDTO;
import com.AgiBank.AgiProtege.dto.DependenteResponseDTO;
import com.AgiBank.AgiProtege.dto.DespesasResponseDTO;
import com.AgiBank.AgiProtege.exception.ResourceNotFoundException;
import com.AgiBank.AgiProtege.model.Dependente;
import com.AgiBank.AgiProtege.model.Vida;
import com.AgiBank.AgiProtege.repository.DependenteRepository;
import com.AgiBank.AgiProtege.repository.VidaRepository;
import org.apache.tomcat.util.compat.JrePlatform;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                () -> new ResourceNotFoundException("Seguro de vida não encontrado!"));

        Dependente dependente = new Dependente();
        dependente.setNome(dto.nome());
        dependente.setParentesco(dto.parentesco());
        dependente.setSeguroVida(seguroVida);

        Dependente dependenteCadastrado = repository.save(dependente);

        calcularPercentual(seguroVida, dependente);

        return toResponseDTO(dependenteCadastrado);
    }

    public List<DependenteResponseDTO> calcularPercentual(Vida seguroVida, Dependente dependente) {

        Double premioTotal = seguroVida.getValorIndenizacaoMorte();
        List<Dependente> dependentes = seguroVida.getDependentes();

        if (dependentes == null || dependentes.isEmpty()) {
            dependentes = new ArrayList<>();
            dependentes.add(dependente);
        }

        Dependente conjuge = null;

        for (Dependente dep : dependentes) {
            if (dep.getParentesco().equalsIgnoreCase("Conjuge")) {
                conjuge = dep;
                break;
            }
        }

        Map<Dependente, Double> percentuais = new HashMap<>();

        Double percentualConjuge = 0.0;
        Double percentualOutros = 0.0;

        if (conjuge != null) {
            percentualConjuge = premioTotal / 2;

            Long numDependentes = (long) (dependentes.size() - 1);
            percentualOutros = (premioTotal - percentualConjuge) / numDependentes;

        } if (conjuge == null) {
            Long numDependentes = (long) dependentes.size();
            percentualOutros = premioTotal / numDependentes;
        }

            for (Dependente dep : dependentes) {
                percentuais.put(dep, percentualOutros);
                dep.setPercentualBeneficio(percentualOutros);
            }

        percentuais.forEach((dep, percentual) -> dep.setPercentualBeneficio(percentual));

        repository.saveAll(dependentes);

        return percentuais.entrySet()
                .stream()
                .map(entry -> new DependenteResponseDTO(
                        entry.getKey().getNome(),
                        entry.getKey().getParentesco(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }

    public DependenteResponseDTO toResponseDTO(Dependente dependente) {
        return new DependenteResponseDTO(

                dependente.getNome(),
                dependente.getParentesco(),
                dependente.getPercentualBeneficio()

        );
    }
}

//correção da branch - arquivos a mais