package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.ClienteResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setSexo(dto.getSexo());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setRenda(dto.getRenda());
        cliente.setIdade(dto.getIdade());
        cliente.setEstadoCivil(dto.getEstadoCivil());

        Cliente clienteCadastrado = repository.save(cliente);

        calcularPerfilDeRiscoInical(clienteCadastrado.getIdCliente());

        return toResponseDTO(clienteCadastrado);
    }

    public ClienteResponseDTO buscarClientePorId(Integer id) {
        Cliente cliente = repository.findById(Long.valueOf(id)).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        return toResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizarClientePorId(Integer id, ClienteRequestDTO dto) {
        Cliente clienteModel = repository.findById(Long.valueOf(id)).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        Cliente clienteAtualizado = Cliente.builder()
                .nome(dto.getNome() != null ? dto.getNome() : clienteModel.getNome())
                .cpf(clienteModel.getCpf())
                .sexo(dto.getSexo() != null ? dto.getSexo() : clienteModel.getSexo())
                .email(dto.getEmail() != null ? dto.getEmail() : clienteModel.getEmail())
                .telefone(dto.getTelefone() != null ? dto.getTelefone() : clienteModel.getTelefone())
                .renda(dto.getRenda() != null ? dto.getRenda() : clienteModel.getRenda())
                .idade(dto.getIdade() != null ? dto.getIdade() : clienteModel.getIdade())
                .estadoCivil(dto.getEstadoCivil() != null ? dto.getEstadoCivil() : clienteModel.getEstadoCivil())
                .perfilRisco(clienteModel.getPerfilRisco())
                .idCliente(clienteModel.getIdCliente())
                .build();

        repository.save(clienteAtualizado);

        return toResponseDTO(clienteAtualizado);
    }

    public void deletarClientePorId(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }

    public void calcularPerfilDeRiscoInical(Integer id) {
        int idade;
        int renda;
        int estadoCivil;
        int sexo;
        int perfilRisco;

        Cliente clienteModel = repository.findById(Long.valueOf(id)).orElseThrow(
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
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setIdade(cliente.getIdade());
        dto.setEstadoCivil(cliente.getEstadoCivil());
        return dto;
    }
}
