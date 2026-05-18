package com.example.ms_ejercicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un ejercicio físico dentro del sistema.
 * Se almacena en la tabla {@code ejercicios}.
 */
@Entity
@Table(name="ejercicios")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ejercicio {

    /**
     * Identificador único del ejercicio.
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    /**
     * Nombre del ejercicio.
     * Es obligatorio y no puede repetirse.
     */
    @Column(nullable = false, unique = true)
    private String nombreEjercicio;

    /**
     * Grupo muscular principal trabajado por el ejercicio.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private GrupoMuscularEnum grupoMuscular;

    /**
     * Nivel de dificultad del ejercicio.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificultadEnum dificultad;
}
