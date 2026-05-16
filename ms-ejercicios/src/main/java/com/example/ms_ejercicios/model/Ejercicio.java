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

@Entity
@Table(name="ejercicios")
@Setter
@Getter
@NoArgsConstructor 
@AllArgsConstructor
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    @Column(nullable = false, unique = true)
    private String nombreEjercicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private GrupoMuscularEnum grupoMuscular;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificultadEnum dificultad;
}
