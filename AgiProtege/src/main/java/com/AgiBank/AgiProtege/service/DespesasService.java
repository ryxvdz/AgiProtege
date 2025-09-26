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

public class DespesasService {
    private final DespesasRepository repository;
    private final ClienteRepository clienteRepository;

    public DespesasService(DespesasRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public DespesasResponseDTO criarSeguroDespesas(DespesasRequestDTO dto){
        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        //Verifica se o cliente já possui algum seguro despesa
        boolean possuiSeguroDespesa = cliente.getApolices().stream()
                .anyMatch(apolice -> "DESPESA".equalsIgnoreCase(apolice.getTipoSeguro()));

        if(possuiSeguroDespesa) {
            throw new RuntimeException("O cliente já possui um Seguro despesa");
        }

        DespesasEssenciais despesas = new DespesasEssenciais();
        despesas.setCliente(cliente);
        despesas.setGastosMensais(dto.gastosMensais());
        despesas.setTempoRegistro(dto.tempoRegistro());
        despesas.setTipoSeguro("DESPESA");
        despesas.setDataFim(LocalDate.now().plusYears(1));
        despesas.setParcela(calcularParcela(dto));

        DespesasEssenciais despesaCadastrada = repository.save(despesas);
        return toResponseDTO (despesaCadastrada);
    }

    public Double calcularParcela(DespesasRequestDTO dto) {
        Double porcentagemParcela = 0.0;
        Double porcentagemTempoTrabalho;

        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente nao encontrado!")
        );

        if(cliente.getPerfilRisco().equals("Baixo")) {
            porcentagemParcela = 0.06;
        }

        if(cliente.getPerfilRisco().equals("Medio")) {
            porcentagemParcela = 0.07;
        }

        if(cliente.getPerfilRisco().equals("Alto")) {
            porcentagemParcela = 0.08;
        }

        Double parcela = (dto.gastosMensais() * 12) * porcentagemParcela / 12;

        if(dto.tempoRegistro() < 6) {
            porcentagemTempoTrabalho = 0.15;
            parcela = parcela + parcela * porcentagemTempoTrabalho;
        }

        return parcela;
    }

    private DespesasResponseDTO toResponseDTO(DespesasEssenciais despesasEssenciais){
        return new DespesasResponseDTO(
                despesasEssenciais.getGastosMensais(),
                despesasEssenciais.getParcela()
        );
    }
}
