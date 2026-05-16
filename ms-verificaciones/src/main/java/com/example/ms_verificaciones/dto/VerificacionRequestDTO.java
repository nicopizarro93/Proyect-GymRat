package com.example.ms_verificaciones.dto;

import com.example.ms_verificaciones.model.enums.TipoValidacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerificacionRequestDTO {

    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    @NotNull(message = "El ID de la marca es obligatorio")
    private Long idMarca;

    @NotNull(message = "El tipo de validación es obligatorio (VIDEO o PRESENCIAL)")
    private TipoValidacion tipoValidacion;

    private String urlVideo;
}
