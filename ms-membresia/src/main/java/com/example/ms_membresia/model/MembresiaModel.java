package com.example.ms_membresia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad JPA que representa una membresía dentro del sistema.
 * Cada registro almacena el plan contratado por un atleta, sus fechas de vigencia y su estado.
 */
@Entity
@Table(name = "membresias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembresiaModel {

    /**
     * Identificador único de la membresía.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * RUT del atleta dueño de la membresía.
     */
    @Column(nullable = false)
    private String rutAtleta;

    /**
     * Tipo de plan contratado por el atleta.
     */
    @Column(nullable = false)
    private String tipoPlan;

    /**
     * Fecha en que inicia la vigencia de la membresía.
     */
    @Column(nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha en que finaliza la vigencia de la membresía.
     */
    @Column(nullable = false)
    private LocalDate fechaFin;

    /**
     * Estado actual de la membresía, por ejemplo ACTIVA o VENCIDA.
     */
    @Column(nullable = false)
    private String estado;
}