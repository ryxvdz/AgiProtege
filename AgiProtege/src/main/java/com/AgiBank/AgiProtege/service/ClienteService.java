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
}
