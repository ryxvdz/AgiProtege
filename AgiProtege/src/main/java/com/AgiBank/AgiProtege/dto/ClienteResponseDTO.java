package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;

public record ClienteResponseDTO(String nome, String email, String telefone, LocalDate idade, String estadoCivil) {
}
