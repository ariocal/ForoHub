package com.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record ActualizarTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String autor,
        @NotBlank
        String curso
) {
}
