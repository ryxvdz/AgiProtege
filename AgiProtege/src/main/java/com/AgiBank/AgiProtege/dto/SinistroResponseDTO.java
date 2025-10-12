package com.AgiBank.AgiProtege.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SinistroResponseDTO(UUID idSinistro, String status, String mensagem, LocalDate dataOcorrencia, String documento, String tipoSeguro) {}
