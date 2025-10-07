package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record AutomovelRequestDTO(UUID idCliente, String placa, Double tabelaFipe, String marca, String modelo,
                                  Integer ano, String categoria, Boolean desastresNaturais,
                                  Boolean carroReserva, Boolean asistencia24) {

    public static AutomovelRequestDTO dadosFIPE(AutomovelRequestDTO dto, FipeDTO fipe) {
        double valorConvertido = converterValorFipe(fipe.valor());
        return new AutomovelRequestDTO(
                dto.idCliente(),
                dto.placa(),
                valorConvertido,      // passa o double convertido
                fipe.marca(),
                fipe.modelo(),
                fipe.ano(),
                dto.categoria(),
                dto.desastresNaturais(),
                dto.carroReserva(),
                dto.asistencia24()
        );
    }
    public static double converterValorFipe(String valor) {
        if (valor == null || valor.isEmpty()) return 0.0;
        String numeroLimpo = valor.replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();
        try {
            return Double.parseDouble(numeroLimpo);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor FIPE inválido: " + valor);
        }
    }
}