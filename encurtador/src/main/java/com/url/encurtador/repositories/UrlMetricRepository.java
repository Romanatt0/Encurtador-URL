package com.url.encurtador.repositories;

import com.url.encurtador.models.UrlMetricModel;
import com.url.encurtador.models.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlMetricRepository extends JpaRepository<UrlMetricModel, Long> {

    Optional<UrlMetricModel> findByUrlAndDiaAndMesAndAno(UrlModel url, Integer dia, Integer mes, Integer ano);

    List<UrlMetricModel> findByUrlOrderByAnoAscMesAscDiaAsc(UrlModel url);

    @Query("SELECT COALESCE(SUM(m.quantidade), 0) FROM UrlMetricModel m WHERE m.url = :url AND m.ano = :ano")
    Long totalByAno(@Param("url") UrlModel url, @Param("ano") Integer ano);

    @Query("SELECT COALESCE(SUM(m.quantidade), 0) FROM UrlMetricModel m WHERE m.url = :url AND m.ano = :ano AND m.mes = :mes")
    Long totalByMes(@Param("url") UrlModel url, @Param("ano") Integer ano, @Param("mes") Integer mes);

    @Query("SELECT COALESCE(SUM(m.quantidade), 0) FROM UrlMetricModel m WHERE m.url = :url AND m.ano = :ano AND m.mes = :mes AND m.dia = :dia")
    Long totalByDia(@Param("url") UrlModel url, @Param("ano") Integer ano, @Param("mes") Integer mes, @Param("dia") Integer dia);
}
