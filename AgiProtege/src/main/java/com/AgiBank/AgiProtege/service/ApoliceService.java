package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.Apolice.ResponseDTO.ApoliceResponseDTO;
import com.AgiBank.AgiProtege.dto.Dependente.ResponseDTO.DependenteResponseDTO;
import com.AgiBank.AgiProtege.model.Apolice;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.Vida;
import com.AgiBank.AgiProtege.repository.ApoliceRepository;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                () -> new RuntimeException("Usuario não encontrado!")
        );

        return toResponseDTO(apolice);
    }
@Transactional
    public List<ApoliceResponseDTO> buscarApolicesPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        return cliente.getApolices().stream()
                .map(apolice -> {
                    List<DependenteResponseDTO> dependentesDTO = null;

                    if (apolice instanceof Vida vida) {
                        dependentesDTO = vida.getDependentes().stream()
                                .map(dep -> new DependenteResponseDTO(dep.getNome(), dep.getParentesco()))
                                .toList();
                    }

                    return new ApoliceResponseDTO(
                            cliente.getNome(),
                            cliente.getEmail(),
                            apolice.getDataInicio(),
                            apolice.getDataFim(),
                            apolice.getParcela(),
                            apolice.getTipoSeguro(),
                            dependentesDTO // pode ser null ou lista preenchida
                    );
                })
                .toList();
    }

    public void deletarApolicePorId(UUID id) {
        apoliceRepository.deleteById(id);
    }

    public ApoliceResponseDTO toResponseDTO(Apolice apolice) {
        List<DependenteResponseDTO> dependentesDTO = null;

        if (apolice instanceof Vida vida) {
            dependentesDTO = vida.getDependentes()
                    .stream()
                    .map(dep -> new DependenteResponseDTO(dep.getNome(), dep.getParentesco()))
                    .toList();
        }

        return new ApoliceResponseDTO(
                apolice.getCliente().getNome(),
                apolice.getCliente().getEmail(),
                apolice.getDataInicio(),
                apolice.getDataFim(),
                apolice.getParcela(),
                apolice.getTipoSeguro(),
                dependentesDTO
        );

        //TOODO FAZER ENUM ATIVO OU INATIVO DOS CLIENTES E DOS SEGUROS
        // IMPLEMENTAR ENUM DOS CLIENTES NA SERVICE USANDO A LOGICA DE ATIVO E INATIVO
        //IMPLEMENTAR ENUM DOS SEGUROS NA SERVICE USANDO A LOGICA DE ATIVO E INATIVO
        // CRIAR UM METODO PARA INATIVAR UM SEGURO
        // CRIAR UM METODO PARA INATIVAR CLIENTE

    }
}
