package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;

public record ClienteRequestDTO(String nome, String cpf, String sexo, String email, String telefone, Double renda, LocalDate idade, String estadoCivil) {
}
