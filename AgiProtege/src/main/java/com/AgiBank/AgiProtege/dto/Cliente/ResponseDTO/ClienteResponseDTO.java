package com.AgiBank.AgiProtege.dto.Cliente.ResponseDTO;

import com.AgiBank.AgiProtege.Enum.EstadoCivil;

public record ClienteResponseDTO( String nome, String email, String telefone, Integer idade, EstadoCivil estadoCivil) {
}
