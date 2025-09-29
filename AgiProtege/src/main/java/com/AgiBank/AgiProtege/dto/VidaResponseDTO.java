package com.AgiBank.AgiProtege.dto;

import lombok.Getter;

@Getter

public record VidaResponseDTO(String profissao, Double imc, Boolean fumante, Double valorIndenizacaoMorte ){
}
