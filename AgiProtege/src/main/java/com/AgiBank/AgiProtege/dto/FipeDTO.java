package com.AgiBank.AgiProtege.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FipeDTO(@JsonProperty("Valor") String valor,
                      @JsonProperty("Marca") String marca,
                      @JsonProperty("Modelo") String modelo,
                      @JsonProperty("AnoModelo") Integer ano) {
}
