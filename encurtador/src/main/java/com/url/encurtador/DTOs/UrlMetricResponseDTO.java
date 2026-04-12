package com.url.encurtador.DTOs;

public record UrlMetricResponseDTO(
        String urlEncurtada,
        Integer dia,
        Integer mes,
        Integer ano,
        Long quantidade
) {
}
