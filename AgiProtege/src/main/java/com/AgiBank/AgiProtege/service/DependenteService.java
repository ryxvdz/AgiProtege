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
    private final VidaRepository vidaRepository;


    public DependenteService(DependenteRepository repository, VidaRepository vidaRepository) {
        this.repository = repository;
        this.vidaRepository = vidaRepository;

    }

    public DependenteResponseDTO adicionarDependente(Vida seguroVida, DependenteRequestDTO dto) {
        Dependente dependente = new Dependente();
        dependente.setNome(dto.nome());
        dependente.setParentesco(dto.parentesco());
        dependente.setSeguroVida(seguroVida); // vincula o dependente ao Vida

        // Calcula o percentual antes de salvar
        calcularPercentual(seguroVida, dependente);

        Dependente dependenteCadastrado = repository.save(dependente);

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
            if ("Conjuge".equalsIgnoreCase(dep.getParentesco())) {
                conjuge = dep;
                break;
            }
        }

        double percentualConjuge = 0.0;
        double percentualOutros = 0.0;
        int numOutros = dependentes.size() - (conjuge != null ? 1 : 0);

        if (conjuge != null) {
            percentualConjuge = numOutros > 0 ? premioTotal / 2 : premioTotal;
            percentualOutros = numOutros > 0 ? (premioTotal - percentualConjuge) / numOutros : 0.0;
        } else {
            percentualOutros = premioTotal / dependentes.size();
        }

        for (Dependente dep : dependentes) {
            if (dep.equals(conjuge)) {
                dep.setPercentualBeneficio(percentualConjuge);
            } else {
                dep.setPercentualBeneficio(percentualOutros);
            }
        }

        return dependentes.stream()
                .map(dep -> new DependenteResponseDTO(
                        dep.getNome(),
                        dep.getParentesco(),
                        dep.getPercentualBeneficio()))
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