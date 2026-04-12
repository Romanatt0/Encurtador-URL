package com.url.encurtador.controllers;

import com.url.encurtador.DTOs.UrlMetricResponseDTO;
import com.url.encurtador.services.UrlMetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    private final UrlMetricService metricService;

    public MetricController(UrlMetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/{codigo}/dia")
    public ResponseEntity<UrlMetricResponseDTO> acessosDia(
            @PathVariable String codigo,
            @RequestParam Integer dia,
            @RequestParam Integer mes,
            @RequestParam Integer ano
    ) {
        return ResponseEntity.ok(metricService.totalPorDia(codigo, ano, mes, dia));
    }

    @GetMapping("/{codigo}/hoje")
    public ResponseEntity<UrlMetricResponseDTO> acessosHoje(@PathVariable String codigo) {
        return ResponseEntity.ok(metricService.totalHoje(codigo));
    }

    @GetMapping("/{codigo}/mes")
    public ResponseEntity<UrlMetricResponseDTO> acessosMes(
            @PathVariable String codigo,
            @RequestParam Integer mes,
            @RequestParam Integer ano
    ) {
        return ResponseEntity.ok(metricService.totalPorMes(codigo, ano, mes));
    }

    @GetMapping("/{codigo}/ano")
    public ResponseEntity<UrlMetricResponseDTO> acessosAno(
            @PathVariable String codigo,
            @RequestParam Integer ano
    ) {
        return ResponseEntity.ok(metricService.totalPorAno(codigo, ano));
    }
}
