package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record DependenteRequestDTO(String nome, String parentesco, UUID seguroVida) {
}
