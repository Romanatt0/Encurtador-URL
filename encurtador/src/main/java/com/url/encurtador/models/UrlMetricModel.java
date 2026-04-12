package com.url.encurtador.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "url_metricas",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_url_metricas_dia",
                columnNames = {"url_id", "dia", "mes", "ano"}
        )
)
public class UrlMetricModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @Column(name = "dia", nullable = false)
    private Integer dia;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "url_id", nullable = false)
    private UrlModel url;
}
