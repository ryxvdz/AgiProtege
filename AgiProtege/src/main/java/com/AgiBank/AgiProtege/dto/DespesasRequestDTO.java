package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record DespesasRequestDTO(UUID idCliente, Double gastosMensais, Double tempoRegistro) {
}
