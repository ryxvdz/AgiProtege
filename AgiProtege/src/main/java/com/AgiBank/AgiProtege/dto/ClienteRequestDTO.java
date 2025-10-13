package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;

public record ClienteRequestDTO(String nome,
                                String cpf,
                                String sexo,
                                String email,
                                String senha,
                                String telefone,
                                Double renda,
                                LocalDate idade,
                                String estadoCivil,
                                String numero,
                                String cep,
                                String logradouro,
                                String bairro,
                                String localidade,
                                String uf) {
}
