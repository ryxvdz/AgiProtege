package com.AgiBank.AgiProtege.dto.Apolice.ResponseDTO;

import com.AgiBank.AgiProtege.dto.Dependente.ResponseDTO.DependenteResponseDTO;

import java.time.LocalDate;
import java.util.List;


public record ApoliceResponseDTO (String nome, String email, LocalDate dataInicio, LocalDate dataFim, Double parcela, String tipoSeguro, List<DependenteResponseDTO> dependentes){
}
