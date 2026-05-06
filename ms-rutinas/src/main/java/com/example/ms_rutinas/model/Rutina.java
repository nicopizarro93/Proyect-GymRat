package com.example.ms_rutinas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "rutinas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    @Column(nullable = false)
    private String nombreRutina;

    @NotNull(message = "la dificultad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificultadEnum dificultad;

    @NotNull(message = "la cantidad de dias es obligatoria")
    @Column(nullable = false)
    private Integer dias;

}
