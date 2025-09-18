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

    public void deletarClientePorId(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }
}
