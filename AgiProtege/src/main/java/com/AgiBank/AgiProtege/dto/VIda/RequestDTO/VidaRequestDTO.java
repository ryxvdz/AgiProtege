package com.AgiBank.AgiProtege.dto.VIda.RequestDTO;

import java.util.UUID;

public record VidaRequestDTO(UUID idCliente, String profissao, Double peso,
                             Double altura, Boolean fumante, Double patrimonio,
                             Boolean historicoFamiliarDoencas, Boolean coberturaHospitalar ){
}