package com.AgiBank.AgiProtege.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seguro_vida")
@PrimaryKeyJoinColumn(name = "id_seguro_vida")
public class Vida extends Apolice {

    @Column(name = "valor_indenizacao_morte")
    private Double valorIndenizacaoMorte;

    private Double peso;

    private Double altura;

    private Double imc=peso/(altura*altura);

    private Boolean fumante;

    private String profissao;

    @OneToMany(mappedBy = "seguroVida", cascade = CascadeType.ALL)
    private List<Dependente> dependentes = new ArrayList<>();
}
