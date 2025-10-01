package com.AgiBank.AgiProtege.dto;

import java.util.UUID;

public record DependenteRequestDTO(UUID seguroVida, String nome, String parentesco) {
}
