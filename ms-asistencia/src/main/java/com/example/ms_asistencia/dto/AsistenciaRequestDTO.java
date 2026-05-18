package com.example.ms_asistencia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO utilizado para recibir la información necesaria
 * al registrar una asistencia.
 */
@Data
public class AsistenciaRequestDTO {

    /**
     * RUT del atleta que intenta registrar su ingreso.
     */
    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;
}
