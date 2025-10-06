package com.AgiBank.AgiProtege.model;


import com.AgiBank.AgiProtege.enums.StatusApolice;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Apolice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_apolice")
    private UUID idApolice;

    @Column(name = "data_inicio")
    private LocalDate dataInicio = LocalDate.now();

    @Column(name = "data_fim")
    private LocalDate dataFim;

    private Double parcela;

    @Column(name = "tipo_seguro")
    private String tipoSeguro;

    //cliente varias apolices - apolice deve ter um cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "apolice", cascade = CascadeType.ALL)
    private List<Sinistro> sinistros = new ArrayList<>();



    @Enumerated(EnumType.STRING)
    @Column(name = "status_apolice")
    private StatusApolice status = StatusApolice.Ativo;

}
