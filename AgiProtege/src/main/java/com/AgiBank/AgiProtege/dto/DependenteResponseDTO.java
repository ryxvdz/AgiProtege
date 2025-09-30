package com.AgiBank.AgiProtege.dto;

import com.AgiBank.AgiProtege.model.Vida;

public record DependenteResponseDTO(Vida seguroVida, String nome, String parentesco) {
}
