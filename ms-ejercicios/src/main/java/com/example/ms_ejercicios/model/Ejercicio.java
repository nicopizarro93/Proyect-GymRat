package com.example.ms_ejercicios.model;


import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "entidad que representa un ejercicio")
@Entity
@Table(name="ejercicios")
@Setter
@Getter
@NoArgsConstructor 
@AllArgsConstructor
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "identificador del ejercicio", example = "1")
    private Long idEjercicio;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del ejercicio", example = "press banca")
    private String nombreEjercicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @Schema(description = "Grupo muscular al que pertenece", example = "pecho")
    private GrupoMuscularEnum grupoMuscular;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "nivel de dificultad", example = "intermedio")
    private DificultadEnum dificultad;
}
