package com.url.encurtador.DTOs;


import lombok.Builder;
import lombok.Data;


public record UrlRequestDTO(

         String url,
         String urlEncurtada
) {
}
