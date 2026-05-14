package com.example.ms_verificaciones.model;

import java.util.HashSet;
import java.util.Set;

import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import com.example.ms_verificaciones.model.enums.TipoValidacion;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "verificaciones")
@Data @NoArgsConstructor @AllArgsConstructor
public class Verificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "el rut del atleta es obligatorio")
    private String rutAtleta;

    @NotNull(message = "el id de la marca es obligatorio")
    private Long idMarca;

    @NotNull(message = "el tipo de validacion es obligatorio (video o presencial)")
    @Enumerated(EnumType.STRING)
    private TipoValidacion tipoValidacion;

    @Enumerated(EnumType.STRING)
    private EstadoValidacion estado;

    private String urlVideo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "verificacion_validadores", joinColumns = @JoinColumn(name = "verificacion_id"))
    @Column(name = "rut_validador")
    private Set<String> rutsValidadores = new HashSet<>();

}
