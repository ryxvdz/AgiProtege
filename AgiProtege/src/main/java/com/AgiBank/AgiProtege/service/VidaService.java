package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.VidaRequestDTO;
import com.AgiBank.AgiProtege.dto.VidaResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.Vida;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.repository.VidaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
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
        vida.setValorIndenizacaoMorte(calculaValorIndenizacaoMorte(cliente, dto));
        vida.setPeso(dto.peso());
        vida.setAltura(dto.altura());
        vida.setFumante(dto.fumante());
        vida.setProfissao(dto.profissao());
        vida.setTipoSeguro("VIDA");
        vida.setDataFim(LocalDate.now().plusYears(1));
        vida.setParcela(calcularParcela(dto, vida));
        vida.setPatrimonio(dto.patrimonio());

        Vida vidaCadastrada = vidaRepository.save(vida);
        return toResponseDTO(vidaCadastrada);
    }

    public Double calculaValorIndenizacaoMorte(Cliente cliente, VidaRequestDTO dto) {
        Double indenizacao = 0.0;

        //Calcula o valor da indenização de acordo com o perfil de risco e patrimonio
        if(cliente.getPerfilRisco().equalsIgnoreCase("Alto")) {
           indenizacao = (cliente.getRenda() * 12 * 5) + (dto.patrimonio() * 0.5);
        }

        if(cliente.getPerfilRisco().equalsIgnoreCase("Medio")) {
            indenizacao = (cliente.getRenda() * 12 * 8) + (dto.patrimonio() * 0.75);
        }

        if(cliente.getPerfilRisco().equalsIgnoreCase("Baixo")) {
            indenizacao = (cliente.getRenda() * 12 * 12) + dto.patrimonio();
        }

        return indenizacao;
    }

    public Double calcularParcela(VidaRequestDTO dto, Vida vida) {
        Double parcela = 0.0;
        Double imc = dto.peso() / (dto.altura() * dto.altura());

        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente nao encontrado!")
        );

        //calculo valor da parcela baseado no perfil de risco do cliente
        if (cliente.getPerfilRisco().equalsIgnoreCase("Alto")) {
            parcela = vida.getValorIndenizacaoMorte() * 0.0005;
        }

        if (cliente.getPerfilRisco().equalsIgnoreCase("Medio")) {
            parcela = vida.getValorIndenizacaoMorte() * 0.0004;
        }

        if (cliente.getPerfilRisco().equalsIgnoreCase("Baixo")) {
            parcela = vida.getValorIndenizacaoMorte() * 0.0003;
        }

        //verifica se o cliente é fumante
        if(dto.fumante()) {
            parcela = parcela + parcela * 0.15;
        }

        //calcula de acordo com o IMC do cliente
        if(imc < 18.5) {
            parcela = parcela + parcela * 0.1;
        }

        if (imc >= 25 && imc < 30) {
            parcela = parcela + parcela * 0.1;
        }

        if(imc >= 30) {
            parcela = parcela + parcela * 0.2;
        }

        return parcela;
    }

    public VidaResponseDTO toResponseDTO(Vida vida) {
        return new VidaResponseDTO(
                vida.getProfissao(),
                vida.getFumante(),
                vida.getValorIndenizacaoMorte(),
                vida.getParcela()
        );
    }
}
