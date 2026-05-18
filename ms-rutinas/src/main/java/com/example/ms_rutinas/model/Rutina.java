package com.example.ms_rutinas.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa una rutina de entrenamiento.
 * Almacena el nombre, dificultad, cantidad de días y los identificadores de ejercicios asociados.
 */
@Entity
@Table(name= "rutinas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rutina {

    /**
     * Identificador único de la rutina.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    /**
     * Nombre de la rutina de entrenamiento.
     */
    @Column(nullable = false)
    private String nombreRutina;

    /**
     * Nivel de dificultad de la rutina.
     */
    @NotNull(message = "la dificultad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificultadEnum dificultad;

    /**
     * Cantidad de días semanales recomendados para realizar la rutina.
     */
    @NotNull(message = "la cantidad de dias es obligatoria")
    @Column(nullable = false)
    private Integer dias;

    /**
     * Identificadores de los ejercicios que componen la rutina.
     */
    @ElementCollection
    private List<Long> ejerciciosIds;

}
