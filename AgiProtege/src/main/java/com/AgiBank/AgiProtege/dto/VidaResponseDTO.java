package com.AgiBank.AgiProtege.dto;

import java.util.List;

public record VidaResponseDTO(String profissao, Boolean fumante, Double valorIndenizacaoMorte, Double parcela, List<DependenteResponseDTO> dependentes){
}
