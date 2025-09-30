package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record AutomovelRequestDTO(UUID idCliente, String placa, Double tabelaFipe, String modelo, Integer ano, String categoria, Boolean desastresNaturais, Boolean carroReserva, Boolean asistencia24) {
}
