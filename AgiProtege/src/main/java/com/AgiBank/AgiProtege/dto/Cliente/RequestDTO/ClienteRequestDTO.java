package com.AgiBank.AgiProtege.dto.Cliente.RequestDTO;

import com.AgiBank.AgiProtege.Enum.EstadoCivil;
import com.AgiBank.AgiProtege.Enum.Sexo;

public record ClienteRequestDTO(String nome, String cpf, Sexo sexo,
                                String email, String telefone,
                                Double renda, Integer idade,
                                EstadoCivil estadoCivil) {
}
