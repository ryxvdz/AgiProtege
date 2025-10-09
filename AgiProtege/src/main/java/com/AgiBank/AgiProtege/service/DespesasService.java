package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.DespesasRequestDTO;
import com.AgiBank.AgiProtege.dto.DespesasResponseDTO;
import com.AgiBank.AgiProtege.enums.StatusApolice;
import com.AgiBank.AgiProtege.exception.ExistingResourceException;
import com.AgiBank.AgiProtege.exception.ResourceNotFoundException;
import com.AgiBank.AgiProtege.exception.ServiceUnavaliable;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.DespesasEssenciais;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.repository.DespesasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service

public class DespesasService {
    private final DespesasRepository repository;
    private final ClienteRepository clienteRepository;

    public DespesasService(DespesasRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public DespesasResponseDTO criarSeguroDespesas(DespesasRequestDTO dto, UUID id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        boolean possuiSeguroDespesaAtivo = cliente.getApolices().stream()
                .anyMatch(apolice ->
                        "DESPESA".equalsIgnoreCase(apolice.getTipoSeguro()) &&
                                apolice.getStatus() == StatusApolice.Ativo
                );

        if(possuiSeguroDespesaAtivo) {
            throw new ExistingResourceException("O cliente já possui um Seguro despesa ativo");
        }

        //verifica se a renda do cliente é maior do que os gastos
        if(cliente.getRenda() < dto.gastosMensais()) {
            throw new ServiceUnavaliable("Serviço indisponivel! Gastos maior que renda mensal!");
        }

        //gasto minimo e maximo para contratar o seguro
        if(dto.gastosMensais() < 500) {
            throw new ServiceUnavaliable("Serviço indisponivel! Gasto minimo R$ 500");
        }

        if(dto.gastosMensais() > 5000) {
            throw new ServiceUnavaliable("Serviço indisponivel! Gasto maximo R$ 5000");
        }

        DespesasEssenciais despesas = new DespesasEssenciais();
        despesas.setCliente(cliente);
        despesas.setGastosMensais(dto.gastosMensais());
        despesas.setTempoRegistro(dto.tempoRegistro());
        despesas.setTipoSeguro("DESPESA");
        despesas.setDataFim(LocalDate.now().plusYears(1));
        despesas.setParcela(calcularParcela(dto, id));

        DespesasEssenciais despesaCadastrada = repository.save(despesas);
        return toResponseDTO (despesaCadastrada);
    }

    public Double calcularParcela(DespesasRequestDTO dto, UUID id) {
        Double porcentagemParcela = 0.0;
        Double porcentagemTempoTrabalho;

        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente nao encontrado!")
        );

        //valor de acordo com o perfil de risco
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

        //variavel do tempo de registro de trabalho
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
