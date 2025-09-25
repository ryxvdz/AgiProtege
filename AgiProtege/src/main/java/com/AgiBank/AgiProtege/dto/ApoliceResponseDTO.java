package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;


public record ApoliceResponseDTO (String nome, String email, LocalDate dataInicio, LocalDate dataFim, Double parcela, String tipoSeguro){
}
