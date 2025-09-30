package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SinistroResponseDTO(
    UUID idSinistro,
    String descricao,
    String status,
    LocalDate dataOcorrencia,
    String documento,
    UUID idApolice
) {}
