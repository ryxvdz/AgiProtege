package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.VidaRequestDTO;
import com.AgiBank.AgiProtege.dto.VidaResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.Vida;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.repository.VidaRepository;

import java.time.LocalDate;

public class VidaService {
    private final VidaRepository vidaRepository;
    private final ClienteRepository clienteRepository;

    public VidaService(VidaRepository vidaRepository, ClienteRepository clienteRepository) {
        this.vidaRepository = vidaRepository;
        this.clienteRepository = clienteRepository;
    }

    public VidaResponseDTO criarSeguroVida(VidaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        boolean possuiSeguroVida = cliente.getApolices().stream()
                .anyMatch(apolice -> "VIDA".equalsIgnoreCase(apolice.getTipoSeguro()));

        if(possuiSeguroVida) {
            throw new RuntimeException("O cliente já possui um Seguro de vida");
        }

        Vida vida = new Vida();
        vida.setCliente(cliente);
        vida.setValorIndenizacaoMorte(dto.valorIndenizacaoMorte());
        vida.setPeso(dto.peso());
        vida.setAltura(dto.altura());
        vida.setFumante(dto.fumante());
        vida.setProfissao(dto.profissao());
        vida.setTipoSeguro("VIDA");
        vida.setDataFim(LocalDate.now().plusYears(1));
        vida.setParcela(calcularParcela(dto));

        Vida vidaCadastrada = vidaRepository.save(vida);
        return toResponseDTO(vidaCadastrada);
    }

    public Double calcularParcela(VidaRequestDTO dto) {
        Double porcentagemParcela = 0.0;
        Double imc = dto.peso() / (dto.altura() * dto.altura());

        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente nao encontrado!")
        );

        // Para cliente que durante cadastro gerou Perfil de Risco "Baixo"
        if(cliente.getPerfilRisco().equals("Médio")) {
            porcentagemParcela = 0.05;
        }

        if(cliente.getPerfilRisco().equals("Médio")) {
            porcentagemParcela = 0.1;
        }

        // Para cliente que gerou IMC maior que 30, assim adiciona 10% na parcela apenas de "Obesidade" para cima
        if(imc >= 30) {
            porcentagemParcela += 0.1;
        }

        // Para fumantes é adicionado 15%
        if(dto.fumante()) {
            porcentagemParcela += 0.15;
        }

        Double parcela = dto.valorIndenizacaoMorte() * porcentagemParcela / 12;

        return parcela;
    }

    public VidaResponseDTO toResponseDTO(Vida vida) {
        return new VidaResponseDTO(
                vida.getProfissao(),
                vida.getImc(),
                vida.getFumante(),
                vida.getValorIndenizacaoMorte()
        );
    }
}
