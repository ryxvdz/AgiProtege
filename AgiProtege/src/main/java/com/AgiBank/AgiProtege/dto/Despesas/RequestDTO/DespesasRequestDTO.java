package com.AgiBank.AgiProtege.dto.Despesas.RequestDTO;

import java.util.UUID;

public record DespesasRequestDTO(UUID idCliente, Double gastosMensais, Double tempoRegistro) {
}
