package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sinistro")
    private Long idSinistro;

    @Column(name = "data_ocorrencia")
    private LocalDate dataOcorrencia;

    private String descricao;

    @Column(name = "valor_reembolsado")
    private Double valorReembolsado;

    private String status;

    @Column(name = "id_apolice")
    private Long idApolice;
}
