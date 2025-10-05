package com.AgiBank.AgiProtege.dto.Automovel.RequestDTO;

import com.AgiBank.AgiProtege.Enum.Categoria;

import java.util.UUID;

public record AutomovelRequestDTO(UUID idCliente, String placa, Double tabelaFipe, String modelo, Integer ano, Categoria categoria, Boolean desastresNaturais, Boolean carroReserva, Boolean asistencia24) {
}
