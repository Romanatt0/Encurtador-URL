package com.url.encurtador.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url_encurtada")
public class UrlModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @URL(message = "O formato da URL é inválido! ")
    @Column(name = "endereco", unique = true, nullable = false )
    private String url;


    @NotBlank
    @Column(name = "urlEncurtada", unique = true, nullable = false )
    private String urlEncurtada;

    @Column(name = "data", nullable = false)
    private LocalDate dataExpiracao = LocalDate.now().plusMonths(3);
}
