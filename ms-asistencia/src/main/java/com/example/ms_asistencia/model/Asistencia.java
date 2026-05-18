package com.example.ms_asistencia.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un registro de asistencia de un atleta.
 * Guarda el RUT, la fecha y hora del ingreso, y el estado del acceso.
 */
@Entity
@Table(name= "asistencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {

    /**
     * Identificador único de la asistencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * RUT del atleta que intenta ingresar.
     */
    @NotBlank(message = "El RUT del atleta no puede estar vacío")
    @Column(name = "rut_atleta", nullable = false)
    private String rutAtleta;

    /**
     * Fecha y hora exacta en que se registró el intento de ingreso.
     */
    @NotNull(message = "La fecha y hora de ingreso es obligatoria")
    @Column(name = "fecha_hora_ingreso", nullable = false)
    private LocalDateTime fechaHoraIngreso;

    /**
     * Estado del ingreso registrado.
     * Puede indicar si el acceso fue permitido o denegado.
     */
    @NotBlank(message = "El estado del ingreso es obligatorio")
    @Column(nullable = false)
    private String estado;
}
