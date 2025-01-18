package com.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        Boolean status,
        String autor,
        String curso
) {
}
