package com.AgiBank.AgiProtege.controller;

import com.AgiBank.AgiProtege.dto.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.ClienteResponseDTO;
import com.AgiBank.AgiProtege.dto.LoginRequestDTO;
import com.AgiBank.AgiProtege.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteService clienteService;

    @PostMapping("/login")
    public ClienteResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return clienteService.login(dto);
    }

    @PostMapping("/cadastrar")
    public ClienteResponseDTO cadastrar(@RequestBody ClienteRequestDTO dto) {
        return clienteService.cadastrarCliente(dto);
    }
}
