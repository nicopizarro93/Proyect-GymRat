package com.example.ms_verificaciones.model;

import java.util.HashSet;
import java.util.Set;

import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import com.example.ms_verificaciones.model.enums.TipoValidacion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una solicitud de verificación de marca.
 * Almacena la información del atleta, la marca asociada,
 * el tipo de validación, el estado actual y los validadores que participaron.
 */
@Entity
@Table(name = "verificaciones")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Verificacion {

    /**
     * Identificador único de la verificación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * RUT del atleta dueño de la marca a verificar.
     */
    @Column(nullable = false)
    private String rutAtleta;

    /**
     * Identificador de la marca asociada a esta solicitud.
     */
    @Column(nullable = false)
    private Long idMarca;

    /**
     * Tipo de validación solicitada para la marca.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoValidacion tipoValidacion;

    /**
     * Estado actual de la solicitud de verificación.
     */
    @Enumerated(EnumType.STRING)
    private EstadoValidacion estado;

    /**
     * URL del video de evidencia, si la validación es de tipo VIDEO.
     */
    private String urlVideo;

    /**
     * Conjunto de RUTs de los usuarios que ya evaluaron esta solicitud.
     * Evita que un mismo validador revise la misma solicitud más de una vez.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "verificacion_validadores", joinColumns = @JoinColumn(name = "verificacion_id"))
    @Column(name = "rut_validador")
    private Set<String> rutsValidadores = new HashSet<>();
}
