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

        Automovel automovelCadastrado = automovelRepository.save(automovel);
        return toResponseDTO(automovelCadastrado);
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
