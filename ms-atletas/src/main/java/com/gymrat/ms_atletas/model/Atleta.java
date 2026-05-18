package com.gymrat.ms_atletas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa a un atleta dentro del sistema.
 *
 * Esta clase se mapea a la tabla "atletas" en la base de datos.
 */
@Entity
@Table(name = "atletas")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Atleta {

    /**
     * Identificador único del atleta.
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * RUT del atleta.
     * Debe ser único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String rut;

    /**
     * Nombre del atleta.
     * No puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Correo electrónico del atleta.
     * No puede ser nulo.
     */
    @Column(nullable = false)
    private String email;

    /**
     * Rol del atleta dentro del sistema.
     * Se guarda como texto en la base de datos.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolEnum rol;
}