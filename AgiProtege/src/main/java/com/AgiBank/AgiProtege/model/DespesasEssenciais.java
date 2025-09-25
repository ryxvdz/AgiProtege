package com.AgiBank.AgiProtege.model;

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
@Table(name = "seguro_despesa")
@PrimaryKeyJoinColumn(name = "id_seguro_despesa")
public class DespesasEssenciais extends Apolice {

    @Column(name = "gastos_mensais")
    private Double gastosMensais;

    @Column(name = "tempo_registro")
    private Double tempoRegistro;
}

