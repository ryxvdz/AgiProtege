package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record SinistroResponseDTO(UUID idSinistro, String status, String mensagem) {}
