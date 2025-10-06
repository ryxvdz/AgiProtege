package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_sinistro")
    private UUID idSinistro;

    @Column(name = "data_ocorrencia")
    private LocalDate dataOcorrencia;

    private String descricao;

    private String status;

    // Caminho do documento enviado pelo cliente
    private String documento;

    //obrigatoriamente 1 apolice
    @ManyToOne
    @JoinColumn(name = "id_apolice", nullable = false)
    private Apolice apolice;
}
