package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dependente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_dependente")
    private UUID idDependente;

    @ManyToOne
    @JoinColumn(name = "id_seguro_vida", nullable = false)
    private Vida seguroVida;

    private String nome;

    private String parentesco;

    private Double percentualBeneficio;
}
