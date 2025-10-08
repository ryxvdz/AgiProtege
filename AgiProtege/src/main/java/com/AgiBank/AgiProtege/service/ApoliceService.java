package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.ApoliceResponseDTO;
import com.AgiBank.AgiProtege.dto.DependenteResponseDTO;
import com.AgiBank.AgiProtege.enums.StatusApolice;
import com.AgiBank.AgiProtege.exception.ResourceNotFoundException;
import com.AgiBank.AgiProtege.exception.ServiceUnavaliable;
import com.AgiBank.AgiProtege.model.*;
import com.AgiBank.AgiProtege.repository.ApoliceRepository;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApoliceService {

    private final ApoliceRepository apoliceRepository;
    private final ClienteRepository clienteRepository;

    public ApoliceService(ApoliceRepository apoliceRepository, ClienteRepository clienteRepository) {
        this.apoliceRepository = apoliceRepository;
        this.clienteRepository = clienteRepository;
    }

    public ApoliceResponseDTO buscarApolicePorId(UUID id) {
        Apolice apolice = apoliceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuario não encontrado!")
        );

        return toResponseDTO(apolice);
    }

    public List<ApoliceResponseDTO> buscarApolicesPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        return cliente.getApolices().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public void inativarApolicePorId(UUID id) {
        Apolice apolice = apoliceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Apólice não encontrada!")
        );

        if (apolice.getStatus() == StatusApolice.Inativo) {
            throw new ServiceUnavaliable("Apólice já está inativa.");
        }

        apolice.setStatus(StatusApolice.Inativo);
        apoliceRepository.save(apolice);
    }

    public ApoliceResponseDTO toResponseDTO(Apolice apolice) {
        List<DependenteResponseDTO> dependentesDTO = null;
        Integer ano = null;
        Boolean assistencia = null;
        Boolean carroReserva = null;
        Boolean desastresNaturais = null;
        Double tabelaFipe = null;
        String categoria = null;
        String modelo = null;
        String placa = null;
        String marca = null;
        Double altura = null;
        Boolean coberturaHospitalar = null;
        Boolean fumante = null;
        Boolean historicoFamiliar = null;
        Double patrimonio = null;
        Double peso = null;
        Double valorIndenizacao = null;
        String profissao = null;
        Double gastosMensais = null;
        Double tempoRegistro = null;

        if (apolice instanceof Vida vida) {
            altura = vida.getAltura();
            coberturaHospitalar = vida.getCoberturaHospitalar();
            fumante = vida.getFumante();
            historicoFamiliar = vida.getHistoricoFamiliarDoencas();
            patrimonio = vida.getPatrimonio();
            peso = vida.getPeso();
            valorIndenizacao = vida.getValorIndenizacaoMorte();
            profissao = vida.getProfissao();

            dependentesDTO = vida.getDependentes()
                    .stream()
                    .map(dep -> new DependenteResponseDTO(dep.getNome(), dep.getParentesco()))
                    .toList();
        }

        if (apolice instanceof Automovel automovel) {
            ano = automovel.getAno();
            assistencia = automovel.getAssistencia24();
            carroReserva = automovel.getCarroReserva();
            desastresNaturais = automovel.getDesastresNaturais();
            tabelaFipe = automovel.getTabelaFipe();
            categoria = automovel.getCategoria();
            modelo = automovel.getModelo();
            placa = automovel.getPlaca();
            marca = automovel.getMarca();
        }

        if (apolice instanceof DespesasEssenciais despesasEssenciais) {
            gastosMensais = despesasEssenciais.getGastosMensais();
            tempoRegistro = despesasEssenciais.getTempoRegistro();
        }

        return new ApoliceResponseDTO(
                apolice.getCliente().getNome(),
                apolice.getCliente().getEmail(),
                apolice.getCliente().getTelefone(),
                apolice.getCliente().getCpf(),
                apolice.getCliente().getIdade(),
                apolice.getCliente().getSexo(),
                apolice.getCliente().getEstadoCivil(),
                apolice.getCliente().getRenda(),
                apolice.getCliente().getPerfilRisco(),
                apolice.getDataInicio(),
                apolice.getDataFim(),
                apolice.getParcela(),
                apolice.getTipoSeguro(),
                ano,
                assistencia,
                carroReserva,
                desastresNaturais,
                tabelaFipe,
                categoria,
                modelo,
                placa,
                marca,
                altura,
                coberturaHospitalar,
                fumante,
                historicoFamiliar,
                patrimonio,
                peso,
                valorIndenizacao,
                profissao,
                dependentesDTO,
                gastosMensais,
                tempoRegistro,
                apolice.getStatus(),
                apolice.getIdApolice()
        );
    }
}
