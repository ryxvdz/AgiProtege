package com.AgiBank.AgiProtege.model;

import com.AgiBank.AgiProtege.enums.StatusApolice;
import com.AgiBank.AgiProtege.enums.StatusCliente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
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

    private String nome;

    @CPF
    @Column(unique = true)
    private String cpf;

    private String sexo;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefone;

    private Double renda;

    private LocalDate idade;

    @Column(name = "estado_Civil")
    private String estadoCivil;

    @Column(name = "perfil_risco")
    private String perfilRisco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Apolice> apolices = new ArrayList<>();

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Endereco endereco;

    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cliente")
    private StatusCliente status = StatusCliente.Ativo;
}
