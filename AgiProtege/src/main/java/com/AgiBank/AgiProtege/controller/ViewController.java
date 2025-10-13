package com.AgiBank.AgiProtege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "cadastrar";
    }

    @GetMapping("/apolices")
    public String apolices() {
        return "apolices";
    }

    @GetMapping("/cadastrarSeguroDespesa")
    public String exibirFormularioSeguroDespesa() {
        return "cadastrarSeguroDespesa";
    }

    @GetMapping("/sinistro")
    public String sinistro() {
        return "sinistro";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "perfil";
    }

    @GetMapping("/cadastrarSeguroVida")
    public String exibirFormularioSeguroVida() {
        return "cadastrarSeguroVida";
    }

    @GetMapping("/MeusSinistros")
    public String sinistros() {
        return "MeusSinistros";
    }

    @GetMapping("/cadastrarSeguroAutomovel")
    public String exibirFormularioSeguroAutomovel() {
        return "cadastrarSeguroAutomovel";
    }

    @GetMapping("/AgiProtege")
    public String home() {
        return "home";
    }

}
