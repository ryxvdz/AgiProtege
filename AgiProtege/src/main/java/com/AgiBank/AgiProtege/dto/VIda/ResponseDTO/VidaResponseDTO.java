package com.AgiBank.AgiProtege.dto.VIda.ResponseDTO;

import com.AgiBank.AgiProtege.dto.Dependente.ResponseDTO.DependenteResponseDTO;

import java.util.List;

public record VidaResponseDTO(String profissao, Boolean fumante, Double valorIndenizacaoMorte,
                              Double parcela, List<DependenteResponseDTO> dependentes){

}
