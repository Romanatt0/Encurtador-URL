package com.url.encurtador.repositories;

import com.url.encurtador.models.UrlModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlModel, Long> {

    Optional<UrlModel> findByUrlEncurtada(String url);

    boolean existsByUrlEncurtada(String url);

    @Modifying
    @Transactional
    @Query("DELETE FROM UrlEncurtada u WHERE u.dataExpiracao < :hoje")
    void deleteExpired(@Param("hoje") LocalDate hoje);
}
