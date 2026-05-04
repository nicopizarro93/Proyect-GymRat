package com.example.ms_asistencia.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "asistencias")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT del atleta no puede estar vacío")
    @Column(name = "rut_atleta", nullable = false)
    private String rutAtleta;

    @NotNull(message = "La fecha y hora de ingreso es obligatoria")
    @Column(name = "fecha_hora_ingreso", nullable = false)
    private LocalDateTime fechaHoraIngreso;

    @NotBlank(message = "El estado del ingreso es obligatorio")
    @Column(nullable = false)
    private String estado;
}
