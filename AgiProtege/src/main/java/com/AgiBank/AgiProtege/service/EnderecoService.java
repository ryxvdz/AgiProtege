package com.AgiBank.AgiProtege.service;


import com.AgiBank.AgiProtege.client.ViaCepClient;
import com.AgiBank.AgiProtege.dto.EnderecoResponseDTO;
import com.AgiBank.AgiProtege.exception.ResourceNotFoundException;
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

    public EnderecoResponseDTO adicionarEnderecoAoCliente(
            UUID id,
            String cep,
            String numero,
            String logradouro,
            String bairro,
            String localidade,
            String uf) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setNumero(numero);
        endereco.setLogradouro(logradouro);
        endereco.setBairro(bairro);
        endereco.setLocalidade(localidade);
        endereco.setUf(uf);
        endereco.setCliente(cliente);

        enderecoRepository.save(endereco);

        return toResponseDTO(endereco);
    }




    public EnderecoResponseDTO buscarEnderecoporId(UUID id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não cadastrado!"));
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
