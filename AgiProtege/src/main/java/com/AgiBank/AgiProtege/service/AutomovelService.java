package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.Enum.Categoria;
import com.AgiBank.AgiProtege.Enum.StatusCliente;
import com.AgiBank.AgiProtege.dto.Automovel.RequestDTO.AutomovelRequestDTO;
import com.AgiBank.AgiProtege.dto.Automovel.ResponseDTO.AutomovelResponseDTO;
import com.AgiBank.AgiProtege.model.Automovel;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.AutomovelRepository;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

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

        if (cliente.getStatusCliente() == StatusCliente.INATIVO) {
            throw new RuntimeException("Cliente está inativo e não pode contratar seguros.");
        }

        //verifica se o carro ja posui seguro
        if(automovelRepository.existsByPlaca(dto.placa())) {
            throw new IllegalArgumentException("Placa já cadastrada");
        }

        //verifica a idade do carro, se oferecemos serviço
        if(LocalDate.now().getYear() - dto.ano() > 12) {
            throw new RuntimeException("Serviço indisponivel! O carro possui mais de 12 anos!");
        }

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
        automovel.setAssistencia24(dto.asistencia24());
        automovel.setCarroReserva(dto.carroReserva());
        automovel.setDesastresNaturais(dto.desastresNaturais());
        automovel.ativar();

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
        if(dto.categoria().equals(Categoria.SEDAN)) {
            parcela = parcela + parcela * 0.05;
        }

        if(dto.categoria().equals(Categoria.SUV)) {
            parcela = parcela + parcela * 0.15;
        }

        if(dto.categoria().equals(Categoria.ESPORTIVO)) {
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
                automovel.getModelo(),
                automovel.getAno(),
                automovel.getCategoria(),
                automovel.getParcela()
        );
    }
    public void deletar(UUID id){
       Automovel automovel = automovelRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Automovel nao encontrado!"));
       automovel.inativa();
        automovelRepository.save(automovel);

    }

    }
