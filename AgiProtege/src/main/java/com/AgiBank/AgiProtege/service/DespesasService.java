package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.DespesasRequestDTO;
import com.AgiBank.AgiProtege.dto.DespesasResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.DespesasEssenciais;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.repository.DespesasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor

public class DespesasService {
    private final DespesasRepository repository;
    private final ClienteRepository clienteRepository;

    public DespesasResponseDTO criarSeguroDespesas(DespesasRequestDTO dto){
        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        DespesasEssenciais despesas = new DespesasEssenciais();
        despesas.setCliente(cliente);
        despesas.setGastosMensais(dto.gastosMensais());
        despesas.setTipoSeguro("DESPESA");
        despesas.setDataFim(LocalDate.now().plusYears(1));

        DespesasEssenciais despesaCadastrada = repository.save(despesas);
        return toResponseDTO (despesaCadastrada);
    }

    private DespesasResponseDTO toResponseDTO(DespesasEssenciais despesasEssenciais){
        return new DespesasResponseDTO(
                despesasEssenciais.getGastosMensais()
        );
    }
}
