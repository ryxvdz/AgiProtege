package com.AgiBank.AgiProtege.dto;

import com.AgiBank.AgiProtege.enums.StatusCliente;

import java.time.LocalDate;

public record ClienteResponseDTO(String nome,
                                 String email,
                                 String cpf,
                                 Double renda,
                                 String sexo,
                                 StatusCliente status,
                                 String telefone,
                                 LocalDate idade,
                                 String estadoCivil,
                                 String bairro,
                                 String logradouro,
                                 String numero,
                                 String token) {
}
