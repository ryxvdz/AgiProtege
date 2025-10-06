package com.AgiBank.AgiProtege.dto;

import com.AgiBank.AgiProtege.enums.StatusCliente;

import java.time.LocalDate;

public record ClienteResponseDTO(String nome, String email, StatusCliente status, String telefone, LocalDate idade, String estadoCivil, String token) {
}
