package com.url.encurtador.services;

import com.url.encurtador.DTOs.UrlMetricResponseDTO;
import com.url.encurtador.models.UrlMetricModel;
import com.url.encurtador.models.UrlModel;
import com.url.encurtador.repositories.UrlMetricRepository;
import com.url.encurtador.repositories.UrlRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UrlMetricService {

    private final UrlMetricRepository metricRepository;
    private final UrlRepository urlRepository;

    public UrlMetricService(UrlMetricRepository metricRepository, UrlRepository urlRepository) {
        this.metricRepository = metricRepository;
        this.urlRepository = urlRepository;
    }

    public void incrementarAcesso(UrlModel url) {
        LocalDate hoje = LocalDate.now();
        int dia = hoje.getDayOfMonth();
        int mes = hoje.getMonthValue();
        int ano = hoje.getYear();

        UrlMetricModel metrica = metricRepository
                .findByUrlAndDiaAndMesAndAno(url, dia, mes, ano)
                .orElseGet(() -> criarMetrica(url, dia, mes, ano));

        metrica.setQuantidade(metrica.getQuantidade() + 1);
        metricRepository.save(metrica);
    }

    public List<UrlMetricResponseDTO> listarDiarias(String urlEncurtada) {
        UrlModel url = buscarUrl(urlEncurtada);

        return metricRepository.findByUrlOrderByAnoAscMesAscDiaAsc(url)
                .stream()
                .map(m -> toResponseDTO(urlEncurtada, m))
                .toList();
    }

    public UrlMetricResponseDTO totalPorAno(String urlEncurtada, Integer ano) {
        UrlModel url = buscarUrl(urlEncurtada);
        Long total = metricRepository.totalByAno(url, ano);
        return new UrlMetricResponseDTO(urlEncurtada, null, null, ano, total);
    }

    public UrlMetricResponseDTO totalPorMes(String urlEncurtada, Integer ano, Integer mes) {
        UrlModel url = buscarUrl(urlEncurtada);
        Long total = metricRepository.totalByMes(url, ano, mes);
        return new UrlMetricResponseDTO(urlEncurtada, null, mes, ano, total);
    }

    public UrlMetricResponseDTO totalPorDia(String urlEncurtada, Integer ano, Integer mes, Integer dia) {
        UrlModel url = buscarUrl(urlEncurtada);
        Long total = metricRepository.totalByDia(url, ano, mes, dia);
        return new UrlMetricResponseDTO(urlEncurtada, dia, mes, ano, total);
    }

    public UrlMetricResponseDTO totalHoje(String urlEncurtada) {
        LocalDate hoje = LocalDate.now();
        return totalPorDia(urlEncurtada, hoje.getYear(), hoje.getMonthValue(), hoje.getDayOfMonth());
    }

    private UrlModel buscarUrl(String urlEncurtada) {
        return urlRepository.findByUrlEncurtada(urlEncurtada)
                .orElseThrow(() -> new EntityNotFoundException("URL encurtada não encontrada: " + urlEncurtada));
    }

    private UrlMetricModel criarMetrica(UrlModel url, int dia, int mes, int ano) {
        UrlMetricModel model = new UrlMetricModel();
        model.setUrl(url);
        model.setDia(dia);
        model.setMes(mes);
        model.setAno(ano);
        model.setQuantidade(0L);
        return model;
    }

    private UrlMetricResponseDTO toResponseDTO(String urlEncurtada, UrlMetricModel model) {
        return new UrlMetricResponseDTO(
                urlEncurtada,
                model.getDia(),
                model.getMes(),
                model.getAno(),
                model.getQuantidade()
        );
    }
}
