package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.ClienteResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
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
                .build();

        repository.save(clienteAtualizado);

        return toResponseDTO(clienteAtualizado);
    }

    public void deletarClientePorId(UUID id) {
        repository.deleteById(id);
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
        if(cliente.getEstadoCivil().equalsIgnoreCase("solteiro")) {
            perfilRisco = perfilRisco + 20;
        }

        //casado menor risco
        if(cliente.getEstadoCivil().equalsIgnoreCase("casado")) {
            perfilRisco = perfilRisco + 10;
        }

        return perfilRisco;
    }

    private int perfilRiscoSexo(Cliente cliente) {
        int perfilRisco = 0;

        //masculino maior risco
        if(cliente.getSexo().equalsIgnoreCase("Masculino")) {
            perfilRisco = perfilRisco + 10;
        }

        //femenino menor risco
        if (cliente.getSexo().equalsIgnoreCase("Femenino")) {
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
}
