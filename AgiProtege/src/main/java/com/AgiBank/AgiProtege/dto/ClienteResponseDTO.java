package com.AgiBank.AgiProtege.dto;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private String nome;
    private String email;
    private String telefone;
    private Integer idade;
    private String estadoCivil;
}
