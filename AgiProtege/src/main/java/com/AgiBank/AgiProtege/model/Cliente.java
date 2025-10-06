package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cliente")
    private UUID idCliente;

//    @NotBlank
    private String nome;

    @CPF
    @Column(unique = true)
    private String cpf;

//    @NotBlank
    private String sexo;

//    @Email
    @Column(unique = true)
    private String email;

//    @NotBlank
    @Column(unique = true)
    private String telefone;

//    @NotNull
    private Double renda;

//    @NotNull
    private Integer idade;

//    @NotBlank
    @Column(name = "estado_Civil")
    private String estadoCivil;

    @Column(name = "perfil_risco")
    private String perfilRisco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Apolice> apolices = new ArrayList<>();


    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Endereco endereco;

    private String senha;
}
