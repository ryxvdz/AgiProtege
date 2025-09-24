package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.DespesasRequestDTO;
import com.AgiBank.AgiProtege.dto.DespesasResponseDTO;
import com.AgiBank.AgiProtege.model.DespesasEssenciais;
import com.AgiBank.AgiProtege.repository.DespesasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class DespesasService {
    private final DespesasRepository repository;

    public DespesasResponseDTO criarSeguroDespesas(DespesasRequestDTO dto){
        DespesasEssenciais despesas = new DespesasEssenciais();
        despesas.setGastosMensais(dto.gastosMensais());

        DespesasEssenciais despesaCadastrada = repository.save(despesas);
        return toResponseDTO (despesaCadastrada);
    }

    private DespesasResponseDTO toResponseDTO(DespesasEssenciais despesasEssenciais){
        return new DespesasResponseDTO(
                despesasEssenciais.getGastosMensais()
        );
    }
}
