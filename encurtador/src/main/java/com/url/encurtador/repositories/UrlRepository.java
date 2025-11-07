package com.url.encurtador.repositories;

import com.url.encurtador.models.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlModel, Long> {

    Optional<UrlModel> findByUrlEncurtada(String url);

    boolean existsByUrlEncurtada(String url);
}
