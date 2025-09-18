package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public void cadastrarCliente(Cliente clinte) {
        repository.save(clinte);
    }

    public Cliente buscarClientePorId(Integer id) {
        return repository.findById(Long.valueOf(id)).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );
    }

    public Cliente atualizarClientePorId(Integer id, Cliente cliente) {
        Cliente clienteModel = repository.findById(Long.valueOf(id)).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!")
        );

        Cliente clienteAtualizado = Cliente.builder()
                .nome(cliente.getNome() != null ? cliente.getNome() : clienteModel.getNome())
                .cpf(clienteModel.getCpf())
                .sexo(cliente.getSexo() != null ? cliente.getSexo() : clienteModel.getSexo())
                .email(cliente.getEmail() != null ? cliente.getEmail() : clienteModel.getEmail())
                .telefone(cliente.getTelefone() != null ? cliente.getTelefone() : clienteModel.getTelefone())
                .renda(cliente.getRenda() != null ? cliente.getRenda() : clienteModel.getRenda())
                .idade(cliente.getIdade() != null ? cliente.getIdade() : clienteModel.getIdade())
                .estadoCivil(cliente.getEstadoCivil() != null ? cliente.getEstadoCivil() : clienteModel.getEstadoCivil())
                .perfilRisco(clienteModel.getPerfilRisco())
                .idCliente(clienteModel.getIdCliente())
                .build();

        repository.save(clienteAtualizado);

        return clienteAtualizado;
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

    public int perfilRiscoSexo(Cliente cliente) {
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
}
