package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.Enum.EstadoCivil;
import com.AgiBank.AgiProtege.Enum.Sexo;
import com.AgiBank.AgiProtege.Enum.StatusCliente;
import com.AgiBank.AgiProtege.Enum.StatusSeguros;
import com.AgiBank.AgiProtege.dto.Cliente.RequestDTO.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.Cliente.ResponseDTO.ClienteResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.AutomovelRepository;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.repository.DespesasRepository;
import com.AgiBank.AgiProtege.repository.VidaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final DespesasRepository despesasRepository;
    private final VidaRepository vidaRepository;
    private final AutomovelRepository automovelRepository;

    public ClienteService(ClienteRepository repository,
                          DespesasRepository despesasRepository,
                          VidaRepository vidaRepository,
                          AutomovelRepository automovelRepository) {
        this.repository = repository;
        this.despesasRepository = despesasRepository;
        this.vidaRepository = vidaRepository;
        this.automovelRepository = automovelRepository;
    }



    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {
        if(dto.idade() < 18) {
            throw new RuntimeException("ERRO! Idade minima 18 anos!");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setSexo(dto.sexo());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setRenda(dto.renda());
        cliente.setIdade(dto.idade());
        cliente.setEstadoCivil(dto.estadoCivil());
        cliente.ativar();

        Cliente clienteCadastrado = repository.save(cliente);

        calcularPerfilDeRiscoInical(clienteCadastrado.getIdCliente());

        return toResponseDTO(clienteCadastrado);
    }

    public ClienteResponseDTO buscarClientePorId(UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        return toResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizarClientePorId(UUID id, ClienteRequestDTO dto) {
        Cliente clienteModel = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );
        if (clienteModel.getStatusCliente() == StatusCliente.INATIVO){
            throw new RuntimeException("Cliente inativo!");
        }

        Cliente clienteAtualizado = Cliente.builder()
                .nome(dto.nome() != null ? dto.nome() : clienteModel.getNome())
                .cpf(clienteModel.getCpf())
                .sexo(dto.sexo() != null ? dto.sexo() : clienteModel.getSexo())
                .email(dto.email() != null ? dto.email() : clienteModel.getEmail())
                .telefone(dto.telefone() != null ? dto.telefone() : clienteModel.getTelefone())
                .renda(dto.renda() != null ? dto.renda() : clienteModel.getRenda())
                .idade(dto.idade() != null ? dto.idade() : clienteModel.getIdade())
                .estadoCivil(dto.estadoCivil() != null ? dto.estadoCivil() : clienteModel.getEstadoCivil())
                .idCliente(clienteModel.getIdCliente())
                .statusCliente(StatusCliente.ATIVO)
                .build();

        repository.save(clienteAtualizado);

        return toResponseDTO(clienteAtualizado);
    }

    public void deletarClientePorId(UUID id) {
       Cliente cliente = repository.findById(id)
               .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

       if (validarSeguro(id)) {

           cliente.inativar();
           repository.save(cliente);
       }
    }

    public void calcularPerfilDeRiscoInical(UUID id) {
        int idade;
        int renda;
        int estadoCivil;
        int sexo;
        int perfilRisco;

        Cliente clienteModel = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        if (clienteModel.getStatusCliente() == StatusCliente.INATIVO){
            throw new RuntimeException("Cliente inativo!");
        }


        idade = perfilRiscoIdade(clienteModel);

        renda = perfilRiscoRenda(clienteModel);

        estadoCivil = perfilRiscoEstadoCivil(clienteModel);

        sexo = perfilRiscoSexo(clienteModel);

        perfilRisco = idade + renda + estadoCivil + sexo;

        if(perfilRisco <= 35) {
            clienteModel.setPerfilRisco("Baixo");
            repository.save(clienteModel);
        }

        if (perfilRisco > 35 && perfilRisco <= 60) {
            clienteModel.setPerfilRisco("Medio");
            repository.save(clienteModel);
        }

        if (perfilRisco > 60) {
            clienteModel.setPerfilRisco("Alto");
            repository.save(clienteModel);
        }
    }

    private int perfilRiscoIdade(Cliente cliente) {
        int perfilRisco = 0;

        //Idade risco alto
        if(cliente.getIdade() <= 25 || cliente.getIdade() > 60) {
            perfilRisco = perfilRisco + 25;
        }

        //Idade risco medio
        if(cliente.getIdade() > 25 && cliente.getIdade() <=40) {
            perfilRisco = perfilRisco + 15;
        }

        //Idade risco Baixo
        if(cliente.getIdade() > 40 && cliente.getIdade() <=60) {
            perfilRisco = perfilRisco + 10;
        }

        return perfilRisco;
    }

    private int perfilRiscoRenda(Cliente cliente) {
        int perfilRisco = 0;

        //baixa renda - alto risco
        if(cliente.getRenda() <= 2000) {
            perfilRisco = perfilRisco + 25;
        }

        //media renda - medio risco
        if(cliente.getRenda() > 2000 && cliente.getRenda() < 5000) {
            perfilRisco = perfilRisco + 15;
        }

        //alta renda - baixo risco
        if(cliente.getRenda() >= 5000) {
            perfilRisco = perfilRisco + 5;
        }

        return perfilRisco;
    }

    private int perfilRiscoEstadoCivil(Cliente cliente) {
        int perfilRisco = 0;

        //solteiro maior risco
        if(cliente.getEstadoCivil().equals(EstadoCivil.SOLTEIRO)) {
            perfilRisco = perfilRisco + 20;
        }

        //casado menor risco
        if(cliente.getEstadoCivil().equals(EstadoCivil.CASADO)) {
            perfilRisco = perfilRisco + 10;
        }

        return perfilRisco;
    }

    private int perfilRiscoSexo(Cliente cliente) {
        int perfilRisco = 0;

        //masculino maior risco
        if(cliente.getSexo().equals(Sexo.MASCULINO)) {
            perfilRisco = perfilRisco + 10;
        }

        //femenino menor risco
        if (cliente.getSexo().equals(Sexo.FEMENINO)) {
            perfilRisco = perfilRisco + 5;
        }

        return perfilRisco;
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getIdade(),
                cliente.getEstadoCivil()
        );
    }
        private boolean validarSeguro(UUID id) {
            boolean temAutomovelAtivo = automovelRepository.existsByClienteIdAndStatus(id, StatusSeguros.CONTRATOATIVO);
            boolean temVidaAtivo = vidaRepository.existsByClienteIdAndStatus(id, StatusSeguros.CONTRATOATIVO);
            boolean temDespesasAtivo = despesasRepository.existsByClienteIdAndStatus(id, StatusSeguros.CONTRATOATIVO);

            if (temAutomovelAtivo || temVidaAtivo || temDespesasAtivo) {
                throw new RuntimeException("Cliente possui contratos ativos, Não pode ser inativado!");

            }else{

                return true;
            }


        }

}
