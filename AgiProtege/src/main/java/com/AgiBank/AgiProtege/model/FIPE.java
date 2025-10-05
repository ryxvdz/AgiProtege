package com.AgiBank.AgiProtege.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class FIPE {

    @Id
    private Integer codigo;
    private String marca;
    private String modelo;
    private Double valor;

}
