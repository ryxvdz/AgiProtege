package com.AgiBank.AgiProtege.model;

import com.AgiBank.AgiProtege.Enum.Categoria;
import com.AgiBank.AgiProtege.Enum.StatusCliente;
import com.AgiBank.AgiProtege.Enum.StatusSeguros;
import jakarta.persistence.*;
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

    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private StatusSeguros statusSeguros;

    @Column(name = "desastres_naturais")
    private Boolean desastresNaturais;

    @Column(name = "carro_reserva")
    private Boolean carroReserva;

    @Column(name = "assistencia_24")
    private Boolean assistencia24;

    public void ativar(){
        this.statusSeguros = StatusSeguros.CONTRATOATIVO;
    }
    public void inativa(){
        this.statusSeguros = StatusSeguros.CONTRATOINATIVO;
    }


}
