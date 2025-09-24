package com.AgiBank.AgiProtege.dto;

public record ClienteRequestDTO(String nome, String cpf, String sexo, String email, String telefone, Double renda, Integer idade, String estadoCivil) {
}
