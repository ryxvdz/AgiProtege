package com.AgiBank.AgiProtege.dto;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nome;
    private String cpf;
    private String sexo;
    private String email;
    private String telefone;
    private Double renda;
    private Integer idade;
    private String estadoCivil;
}
