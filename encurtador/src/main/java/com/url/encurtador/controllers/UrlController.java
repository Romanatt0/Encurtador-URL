package com.url.encurtador.controllers;

import com.url.encurtador.DTOs.UrlRequestDTO;
import com.url.encurtador.DTOs.UrlResponseDTO;
import com.url.encurtador.exceptions.UrlAlreadyExistsException;
import com.url.encurtador.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/dev")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/encurtar")
    public ResponseEntity<UrlResponseDTO> encurtar(@Valid @RequestBody UrlRequestDTO request) {
        UrlResponseDTO response = urlService.encurtarUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{urlEncurtada}")
    public ResponseEntity<UrlResponseDTO>redirecionar(@PathVariable String urlEncurtada){

        UrlResponseDTO dto = urlService.findByUrlEncurtada(urlEncurtada);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(dto.url()))
                .build();
    }

}
