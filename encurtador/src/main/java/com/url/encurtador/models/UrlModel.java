package com.url.encurtador.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "url_encurtada")
public class UrlModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @URL(message = "O formato da URL é inválido! ")
    @Column(name = "endereco", unique = true, nullable = false )
    private String url;


    @NotBlank
    @Column(name = "urlEncurtada", unique = true, nullable = false )
    private String urlEncurtada;

    @NotBlank
    @Column(name = "data", nullable = false)
    private LocalDate dataExpiracao = LocalDate.now().plusMonths(3);;
}
