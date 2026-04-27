package com.example.ms_marcas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marcas")
@Data @NoArgsConstructor @AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    @NotBlank(message = "debe especificar el nombre del ejercicio")
    @Column(name = "nombre_ejercicio")
    private String nombreEjercicio;

    @NotNull(message = "el peso levantado es obligatorio")
    @Min(value = 0, message = "el peso levantado no puede ser negativo")
    @Column(name = "peso_levantado")
    private Double pesoLevantado;

    @Column(nullable = false)
    private String estado;

}
