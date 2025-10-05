package com.AgiBank.AgiProtege.dto.Despesas.ResponseDTO;

import com.AgiBank.AgiProtege.Enum.StatusSeguros;

public record DespesasResponseDTO(Double gastosMensais, Double parcela, StatusSeguros statusSeguros) {

}
