package com.url.encurtador.services;

import com.url.encurtador.DTOs.UrlRequestDTO;
import com.url.encurtador.DTOs.UrlResponseDTO;
import com.url.encurtador.exceptions.UrlAlreadyExistsException;
import com.url.encurtador.models.UrlModel;
import com.url.encurtador.repositories.UrlRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public UrlResponseDTO encurtarUrl(UrlRequestDTO request) {

        if (repository.existsByUrlEncurtada(request.urlEncurtada())) {
            throw new UrlAlreadyExistsException("A URL encurtada já está em uso: " + request.urlEncurtada());
        }

        UrlModel model = new UrlModel();
        model.setUrl(request.url());
        model.setUrlEncurtada(request.urlEncurtada());

        UrlModel saved = repository.save(model);

        return new UrlResponseDTO(saved.getUrl(), saved.getUrlEncurtada(), saved.getDataExpiracao());
    }

    public UrlResponseDTO findByUrlEncurtada(String urlEncurtada) {
        UrlModel model = repository.findByUrlEncurtada(urlEncurtada)
                .orElseThrow(() -> new EntityNotFoundException("URL encurtada não encontrada: " + urlEncurtada));

        return new UrlResponseDTO(model.getUrl(), model.getUrlEncurtada(), model.getDataExpiracao());
    }
}
