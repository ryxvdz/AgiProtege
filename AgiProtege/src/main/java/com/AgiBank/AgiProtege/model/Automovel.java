package com.AgiBank.AgiProtege.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seguro_automovel")
@PrimaryKeyJoinColumn(name = "id_seguro_automovel")
public class Automovel extends Apolice {

    private String placa;

    @Column(name = "tabela_fipe")
    private Double tabelaFipe;

    private String modelo;

    private Integer ano;

    private String categoria;

    @Column(name = "desastres_naturais")
    private Boolean desastresNaturais;

    @Column(name = "carro_reserva")
    private Boolean carroReserva;

    @Column(name = "assistencia_24")
    private Boolean assistencia24;
}
