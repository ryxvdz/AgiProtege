package com.AgiBank.AgiProtege.dto;

import com.AgiBank.AgiProtege.enums.StatusApolice;

import java.time.LocalDate;
import java.util.List;


public record ApoliceResponseDTO (String nome, String email, LocalDate dataInicio, LocalDate dataFim, Double parcela, String tipoSeguro, List<DependenteResponseDTO> dependentes, StatusApolice status){
}
