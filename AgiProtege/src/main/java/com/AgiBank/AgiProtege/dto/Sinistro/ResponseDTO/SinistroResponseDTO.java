package com.AgiBank.AgiProtege.dto.Sinistro.ResponseDTO;

import java.util.UUID;

public record SinistroResponseDTO(UUID idSinistro, String status, String mensagem) {}
