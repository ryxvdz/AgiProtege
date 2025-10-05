package com.AgiBank.AgiProtege.dto.Automovel.ResponseDTO;

import com.AgiBank.AgiProtege.Enum.Categoria;

public record AutomovelResponseDTO(
                                   String placa,
                                   Double tabelaFipe,
                                   String modelo,
                                   Integer ano,
                                   Categoria categoria,
                                   Double parcela) {


}
