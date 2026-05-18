package com.example.ms_verificaciones.dto;

import com.example.ms_verificaciones.model.enums.TipoValidacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO utilizado para crear una nueva solicitud de verificación
 * asociada a una marca registrada por un atleta.
 */
@Data
public class VerificacionRequestDTO {

    /**
     * RUT del atleta que solicita la verificación.
     */
    @NotBlank(message = "El RUT del atleta es obligatorio")
    private String rutAtleta;

    /**
     * Identificador de la marca que se desea verificar.
     */
    @NotNull(message = "El ID de la marca es obligatorio")
    private Long idMarca;

    /**
     * Tipo de validación solicitada.
     * Puede ser mediante video o de forma presencial.
     */
    @NotNull(message = "El tipo de validación es obligatorio (VIDEO o PRESENCIAL)")
    private TipoValidacion tipoValidacion;

    /**
     * URL del video de evidencia.
     * Es obligatoria cuando el tipo de validación es VIDEO.
     */
    private String urlVideo;
}
