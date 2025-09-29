package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.AutomovelRequestDTO;
import com.AgiBank.AgiProtege.dto.AutomovelResponseDTO;
import com.AgiBank.AgiProtege.model.Automovel;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.AutomovelRepository;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AutomovelService {

    private final AutomovelRepository automovelRepository;
    private final ClienteRepository clienteRepository;

    public AutomovelService(AutomovelRepository automovelRepository, ClienteRepository clienteRepository) {
        this.automovelRepository = automovelRepository;
        this.clienteRepository= clienteRepository;
    }


    public AutomovelResponseDTO criarSeguroAutomovel(AutomovelRequestDTO dto){

        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                ()-> new RuntimeException("Cliente não encontrado!")
        );

        Automovel automovel = new Automovel();
        automovel.setCliente(cliente);
        automovel.setPlaca(dto.placa());
        automovel.setTabelaFipe(dto.tabelaFipe());
        automovel.setModelo(dto.modelo());
        automovel.setAno(dto.ano());
        automovel.setCategoria(dto.categoria());
        automovel.setTipoSeguro("AUTO");
        automovel.setDataFim(LocalDate.now().plusYears(1));
        automovel.setParcela(calcularParcela(dto));

        Automovel automovelCadastrado = automovelRepository.save(automovel);
        return toResponseDTO(automovelCadastrado);
    }

    public Double calcularParcela(AutomovelRequestDTO dto) {
        Double porcentagemTabelFipe = 0.0;

        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        //porcentagem anual da tabela fipe de acordo com o perfil de risco
        if(cliente.getPerfilRisco().equalsIgnoreCase("Baixo")) {
            porcentagemTabelFipe = 0.03;
        }

        if(cliente.getPerfilRisco().equalsIgnoreCase("Medio")) {
            porcentagemTabelFipe = 0.04;
        }

        if(cliente.getPerfilRisco().equalsIgnoreCase("Alto")) {
            porcentagemTabelFipe = 0.05;
        }

        //calculo parcela inical
        Double parcela = (dto.tabelaFipe() * porcentagemTabelFipe) / 12;

        //calculo parcela de acordo com a categoria do carro
        if(dto.categoria().equalsIgnoreCase("Sedan")) {
            parcela = parcela + parcela * 0.05;
        }

        if(dto.categoria().equalsIgnoreCase("SUV")) {
            parcela = parcela + parcela * 0.15;
        }

        if(dto.categoria().equalsIgnoreCase("Esportivo")) {
            parcela = parcela + parcela * 0.30;
        }

        return parcela;
    }

    private AutomovelResponseDTO toResponseDTO(Automovel automovel) {
        return new AutomovelResponseDTO(
                automovel.getPlaca(),
                automovel.getTabelaFipe(),
                automovel.getModelo(),
                automovel.getAno(),
                automovel.getCategoria()
        );
    }
}
