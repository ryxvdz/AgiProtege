package com.AgiBank.AgiProtege.model;

import com.AgiBank.AgiProtege.Enum.EstadoCivil;
import com.AgiBank.AgiProtege.Enum.Sexo;
import com.AgiBank.AgiProtege.Enum.StatusCliente;
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
    private String cpf;

//    @NotBlank
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

//    @Email
    private String email;

//    @NotBlank
    private String telefone;

//    @NotNull
    private Double renda;

//    @NotNull
    private Integer idade;

    @Enumerated(EnumType.STRING)
    private StatusCliente statusCliente;

//    @NotBlank
    @Column(name = "estado_Civil")
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @Column(name = "perfil_risco")
    private String perfilRisco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Apolice> apolices = new ArrayList<>();

    public void ativar(){
        this.statusCliente = StatusCliente.ATIVO;
    }

    public void inativar(){
        this.statusCliente = StatusCliente.INATIVO;
    }


}
