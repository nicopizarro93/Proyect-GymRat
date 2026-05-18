package com.example.ms_marcas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una marca registrada por un atleta.
 * Almacena el ejercicio realizado, el peso levantado y el estado de aprobación.
 */
@Entity
@Table(name = "marcas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marca {

    /**
     * Identificador único de la marca.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * RUT del atleta asociado a la marca.
     */
    @Column(name = "rut_atleta", nullable = false)
    private String rutAtleta;

    /**
     * Nombre del ejercicio realizado por el atleta.
     */
    @Column(name = "nombre_ejercicio", nullable = false)
    private String nombreEjercicio;

    /**
     * Peso levantado por el atleta durante el ejercicio.
     */
    @Column(name = "peso_levantado", nullable = false)
    private Double pesoLevantado;

    /**
     * Estado actual de la marca, por ejemplo: PENDIENTE, APROBADA o RECHAZADA.
     */
    @Column(nullable = false)
    private String estado;
}
