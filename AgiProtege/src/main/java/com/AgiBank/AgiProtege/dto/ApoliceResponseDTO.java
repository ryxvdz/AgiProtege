package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ApoliceResponseDTO (UUID idCliente, LocalDate dataInicio, LocalDate dataFim, Double parcela, String tipoSeguro){
}
