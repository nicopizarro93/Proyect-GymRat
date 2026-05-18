package com.example.ms_membresia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO utilizado para recibir los datos necesarios al contratar una membresía.
 * Incluye validaciones para asegurar que la solicitud contenga información válida.
 */
@Data
public class MembresiaRequestDTO {

    /**
     * RUT del atleta que contratará la membresía.
     */
    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    /**
     * Tipo de plan solicitado, por ejemplo mensual, trimestral o anual.
     */
    @NotBlank(message = "El tipo de plan es obligatorio")
    private String tipoPlan;

    /**
     * Cantidad de meses que durará la membresía contratada.
     */
    @NotNull(message = "Los meses de duración son obligatorios")
    @Min(value = 1, message = "La duración mínima del plan es de 1 mes")
    private Integer mesesDuracion;
}
