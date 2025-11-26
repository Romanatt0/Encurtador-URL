package com.url.encurtador.DTOs;

import java.time.LocalDate;

public record UrlResponseDTO(
        String url,
        String urlEncurtada,
        LocalDate dataExpiracao
) {
}
