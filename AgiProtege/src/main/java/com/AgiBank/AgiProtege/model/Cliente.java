package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @NotBlank
    private String nome;

    @CPF
    private String cpf;

    @NotBlank
    private String sexo;

    @Email
    private String email;

    @NotBlank
    private String telefone;

    @NotNull
    private Double renda;

    @NotNull
    private Integer idade;

    @NotBlank
    @Column(name = "estado_Civil")
    private String estadoCivil;

    @Column(name = "perfil_risco")
    private String perfilRisco;
}
