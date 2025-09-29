package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record VidaRequestDTO(UUID idCliente, String profissao, Double peso, Double altura, Boolean fumante, Double valorIndenizacaoMorte ){
}