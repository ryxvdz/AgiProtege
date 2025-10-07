package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.enums.StatusApolice;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.ApoliceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.AgiBank.AgiProtege.model.Apolice;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final ApoliceRepository apoliceRepository;

    public ViewController(ApoliceRepository apoliceRepository) {
        this.apoliceRepository = apoliceRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "cadastrar";
    }

    @GetMapping("/gerenciarApolices")
    public String gerenciarApolices(Model model, Authentication authentication) {
        // Recupera o cliente logado
        Cliente cliente = (Cliente) authentication.getPrincipal();

        // Busca todas as apólices desse cliente
        List<Apolice> apolicesDoCliente = apoliceRepository.findByClienteAndStatus(cliente, StatusApolice.Ativo);
        apolicesDoCliente.addAll(apoliceRepository.findByClienteAndStatus(cliente, StatusApolice.Inativo));

        // Filtra por status (enum)
        List<Apolice> apolicesAtivas = apolicesDoCliente.stream()
                .filter(a -> a.getStatus() == StatusApolice.Ativo)
                .collect(Collectors.toList());

        List<Apolice> apolicesInativas = apolicesDoCliente.stream()
                .filter(a -> a.getStatus() == StatusApolice.Inativo)
                .collect(Collectors.toList());

        // Adiciona ao modelo pra renderizar no Thymeleaf
        model.addAttribute("apolicesAtivas", apolicesAtivas);
        model.addAttribute("apolicesInativas", apolicesInativas);

        return "gerenciarApolices"; // → Thymeleaf vai procurar em templates/gerenciarApolices.html
    }
}
