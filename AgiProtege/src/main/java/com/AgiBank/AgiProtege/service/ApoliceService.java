package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.ApoliceResponseDTO;
import com.AgiBank.AgiProtege.model.Apolice;
import com.AgiBank.AgiProtege.repository.ApoliceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApoliceService {

    private final ApoliceRepository apoliceRepository;

    public ApoliceService(ApoliceRepository apoliceRepository) {
        this.apoliceRepository = apoliceRepository;
    }

    public ApoliceResponseDTO buscarApolicePorId(UUID id) {
        Apolice apolice = apoliceRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado!")
        );

        return toResponseDTO(apolice);
    }

    public void deletarApolicePorId(UUID id) {
        apoliceRepository.deleteById(id);
    }

    private ApoliceResponseDTO toResponseDTO(Apolice apolice) {
        return new ApoliceResponseDTO(
                apolice.getCliente().getNome(),
                apolice.getCliente().getEmail(),
                apolice.getDataInicio(),
                apolice.getDataFim(),
                apolice.getParcela(),
                apolice.getTipoSeguro()

        );
    }
}
