package com.url.encurtador.repositories;

import com.url.encurtador.models.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlModel, Long> {

    UrlModel findByUrlEncurtada(String url);

    boolean existsByUrlEncurtada(String url);
}
