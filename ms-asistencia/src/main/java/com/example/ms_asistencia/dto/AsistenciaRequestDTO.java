package com.example.ms_asistencia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AsistenciaRequestDTO {

    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

}
