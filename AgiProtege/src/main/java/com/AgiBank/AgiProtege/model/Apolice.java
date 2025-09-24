package com.AgiBank.AgiProtege.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Apolice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apolice")
    private Long idApolice;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    private Double parcela;

    @Column(name = "tipo_seguro")
    private String tipoSeguro;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_seguro_vida")
    private Long idSeguroVida;

    @Column(name = "id_seguro_automovel")
    private Long idSeguroAutomovel;

    @OneToOne(mappedBy = "apolice", cascade = CascadeType.ALL)
    private DespesasEssenciais despesasEssenciais;
}
