package com.AgiBank.AgiProtege.dto;

import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public record SinistroRequestDTO(
    String descricao,
    UUID idApolice,
    MultipartFile documento
) {}
