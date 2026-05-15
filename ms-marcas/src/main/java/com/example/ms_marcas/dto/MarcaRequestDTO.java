package com.example.ms_marcas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarcaRequestDTO {
    
    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    @NotBlank(message = "Debe especificar el nombre del ejercicio")
    private String nombreEjercicio;

    @NotNull(message = "El peso levantado es obligatorio")
    @Min(value = 0, message = "El peso levantado no puede ser negativo")
    private Double pesoLevantado;
}
