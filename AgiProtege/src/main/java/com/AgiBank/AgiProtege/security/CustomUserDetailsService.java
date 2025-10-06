package com.AgiBank.AgiProtege.security;

import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = this.repository.findByCpf(username).orElseThrow(
                () -> new UsernameNotFoundException("Cliente não encontrado")
        );

        return new org.springframework.security.core.userdetails.User(cliente.getCpf(), cliente.getSenha(), new ArrayList<>());
    }
}
