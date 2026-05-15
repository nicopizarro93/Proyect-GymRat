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

@Entity
@Table(name = "verificaciones")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Verificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rutAtleta;

    @Column(nullable = false)
    private Long idMarca;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoValidacion tipoValidacion;

    @Enumerated(EnumType.STRING)
    private EstadoValidacion estado;

    private String urlVideo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "verificacion_validadores", joinColumns = @JoinColumn(name = "verificacion_id"))
    @Column(name = "rut_validador")
    private Set<String> rutsValidadores = new HashSet<>();
}
