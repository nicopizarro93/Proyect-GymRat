package com.example.ms_marcas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO utilizado para recibir los datos necesarios al registrar una nueva marca.
 * Contiene la información del atleta, el ejercicio y el peso levantado.
 */
@Data
public class MarcaRequestDTO {

    /**
     * RUT del atleta que registra la marca.
     * Es obligatorio para asociar la marca a un atleta existente.
     */
    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    /**
     * Nombre del ejercicio en el que se registró la marca.
     */
    @NotBlank(message = "Debe especificar el nombre del ejercicio")
    private String nombreEjercicio;

    /**
     * Peso levantado por el atleta.
     * Debe ser obligatorio y no puede tener un valor negativo.
     */
    @NotNull(message = "El peso levantado es obligatorio")
    @Min(value = 0, message = "El peso levantado no puede ser negativo")
    private Double pesoLevantado;
}
