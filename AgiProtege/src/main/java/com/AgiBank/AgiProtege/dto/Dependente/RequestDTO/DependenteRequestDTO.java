package com.AgiBank.AgiProtege.dto.Dependente.RequestDTO;

import java.util.UUID;

public record DependenteRequestDTO(UUID seguroVida, String nome, String parentesco) {
}
