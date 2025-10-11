package com.AgiBank.AgiProtege.dto;

import java.util.List;

public record VidaRequestDTO(String profissao, Double peso, Double altura, Boolean fumante, Double patrimonio, Boolean historicoFamiliarDoencas, Boolean coberturaHospitalar, List<DependenteRequestDTO> dependentes ){
}