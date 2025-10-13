package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

    @Id
    @Column(name = "id_endereco")
    private UUID idEndereco;

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}