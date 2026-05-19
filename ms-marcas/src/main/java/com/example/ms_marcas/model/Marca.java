package com.example.ms_marcas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marcas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut_atleta", nullable = false)
    private String rutAtleta;

    @Column(name = "nombre_ejercicio", nullable = false)
    private String nombreEjercicio;

    @Column(name = "peso_levantado", nullable = false)
    private Double pesoLevantado;

    @Column(nullable = false)
    private EstadoEnum estado;
}
