package com.forohub.domain.topico;

import java.time.LocalDateTime;

public record ListarTopicos(
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        Boolean status,
        String autor,
        String curso
) {
}
