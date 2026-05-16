package com.example.ms_membresia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MembresiaRequestDTO {

    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    @NotBlank(message = "El tipo de plan es obligatorio")
    private String tipoPlan;

    @NotNull(message = "Los meses de duración son obligatorios")
    @Min(value = 1, message = "La duración mínima del plan es de 1 mes")
    private Integer mesesDuracion;
}
