package com.example.ms_membresia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity // esta clase será una tabla en la base de datos
@Table(name = "membresias") // Nombramos la tabla
@Getter
@Setter
@NoArgsConstructor // crea un constructor vacío
@AllArgsConstructor //crea un constructor con todos los datos
public class MembresiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // El ID único

    @Column(nullable = false)
    private String rutAtleta; // El RUT del cliente

    @Column(nullable = false)
    private String tipoPlan; // Ej: "Mensual", "Trimestral", "Anual"

    @Column(nullable = false)
    private LocalDate fechaInicio; // Cuándo pagó/inició

    @Column(nullable = false)
    private LocalDate fechaFin; // Cuándo se le vence

    @Column(nullable = false)
    private String estado; // Ej: "ACTIVA" o "VENCIDA"

}