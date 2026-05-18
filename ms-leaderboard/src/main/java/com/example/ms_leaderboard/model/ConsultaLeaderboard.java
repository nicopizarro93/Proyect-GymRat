package com.example.ms_leaderboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que registra una consulta realizada al leaderboard.
 * Permite almacenar estadísticas sobre los ejercicios consultados.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultas_leaderboard")
public class ConsultaLeaderboard {

    /**
     * Identificador único de la consulta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del ejercicio consultado en el leaderboard.
     */
    private String nombreEjercicio;

    /**
     * Fecha y hora en que se realizó la consulta.
     */
    private LocalDateTime fechaConsulta;
}
