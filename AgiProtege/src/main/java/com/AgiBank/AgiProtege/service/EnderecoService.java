package com.AgiBank.AgiProtege.service;


import com.AgiBank.AgiProtege.client.ViaCepClient;
import com.AgiBank.AgiProtege.dto.EnderecoResponseDTO;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.model.Endereco;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnderecoService {

    private final ViaCepClient viaCepClient;
    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;


    public EnderecoService(ViaCepClient viaCepClient, EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
        this.viaCepClient = viaCepClient;
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
    }

    public EnderecoResponseDTO adicionarEnderecoAoCliente(UUID idCliente, String cep, String numero) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        EnderecoResponseDTO enderecoViaCep = viaCepClient.buscarEndereco(cep);

        Endereco endereco = new Endereco();
        endereco.setCliente(cliente); // idEndereco será o mesmo que cliente.idCliente
        endereco.setCep(enderecoViaCep.cep());
        endereco.setLogradouro(enderecoViaCep.logradouro());
        endereco.setBairro(enderecoViaCep.bairro());
        endereco.setLocalidade(enderecoViaCep.localidade());
        endereco.setUf(enderecoViaCep.uf());
        endereco.setNumero(numero);

        enderecoRepository.save(endereco);

        return toResponseDTO(endereco);
    }



    public EnderecoResponseDTO buscarEnderecoporId(UUID id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não cadastrado!"));
        return toResponseDTO(endereco);
    }


    public EnderecoResponseDTO toResponseDTO(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getLocalidade(),
                endereco.getUf(),
                endereco.getNumero()
        );
    }

}
