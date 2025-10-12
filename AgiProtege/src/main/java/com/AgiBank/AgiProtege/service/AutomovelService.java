package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.AutomovelRequestDTO;
import com.AgiBank.AgiProtege.dto.AutomovelResponseDTO;
import com.AgiBank.AgiProtege.dto.FipeDTO;
import com.AgiBank.AgiProtege.exception.ExistingResourceException;
import com.AgiBank.AgiProtege.exception.ResourceNotFoundException;
import com.AgiBank.AgiProtege.exception.ServiceUnavaliable;
import com.AgiBank.AgiProtege.model.Automovel;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.AutomovelRepository;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.AgiBank.AgiProtege.dto.AutomovelRequestDTO.converterValorFipe;

@Service
public class AutomovelService {

    private final AutomovelRepository automovelRepository;
    private final ClienteRepository clienteRepository;
    private final FipeService fipeService;

    public AutomovelService(AutomovelRepository automovelRepository, ClienteRepository clienteRepository, FipeService fipeService) {
        this.automovelRepository = automovelRepository;
        this.clienteRepository = clienteRepository;
        this.fipeService = fipeService;
    }

    public AutomovelResponseDTO criarSeguroAutomovel(AutomovelRequestDTO dto, UUID id){

        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Cliente não encontrado!")
        );

        //verifica se o carro ja posui seguro
        if(automovelRepository.existsByPlaca(dto.placa())) {
            throw new ExistingResourceException("Placa já cadastrada");
        }

        //verifica a idade do carro, se oferecemos serviço
        if(LocalDate.now().getYear() - dto.ano() > 12) {
            throw new ServiceUnavaliable("Serviço indisponivel! O carro possui mais de 12 anos!");
        }

        FipeDTO fipeDTO = fipeService.buscarPorNome(dto.marca(), dto.modelo(), String.valueOf(dto.ano()));
        double valorTabela = AutomovelRequestDTO.converterValorFipe(fipeDTO.valor());

        Automovel automovel = new Automovel();
        automovel.setCliente(cliente);
        automovel.setPlaca(dto.placa());
        automovel.setTabelaFipe(valorTabela);
        automovel.setModelo(fipeDTO.modelo());
        automovel.setMarca(fipeDTO.marca());
        automovel.setAno(dto.ano());
        automovel.setCategoria(dto.categoria());
        automovel.setTipoSeguro("AUTO");
        automovel.setDataFim(LocalDate.now().plusYears(1));
        automovel.setParcela(calcularParcela(dto, valorTabela, id));
        automovel.setAssistencia24(dto.asistencia24());
        automovel.setCarroReserva(dto.carroReserva());
        automovel.setDesastresNaturais(dto.desastresNaturais());

        Automovel automovelCadastrado = automovelRepository.save(automovel);
        return toResponseDTO(automovelCadastrado);
    }

    public Double calcularParcela(AutomovelRequestDTO dto, double valorTabelFipe, UUID id) {
        Double porcentagemTabelFipe = 0.0;

        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
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
        Double parcela = (valorTabelFipe * porcentagemTabelFipe) / 12;

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

        //assistencia 24 horas
        if(dto.asistencia24()) {
            parcela = parcela + parcela * 0.04;
        }

        //carro reserva
        if(dto.carroReserva()) {
            parcela = parcela + parcela * 0.05;
        }

        //desastres naturais
        if(dto.desastresNaturais()) {
            parcela = parcela + parcela * 0.04;
        }

        return parcela;
    }

    private AutomovelResponseDTO toResponseDTO(Automovel automovel) {
        return new AutomovelResponseDTO(
                automovel.getPlaca(),
                automovel.getTabelaFipe(),
                automovel.getMarca(),
                automovel.getModelo(),
                automovel.getAno(),
                automovel.getCategoria(),
                automovel.getParcela()
        );
    }
}
