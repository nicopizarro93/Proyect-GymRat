package com.example.ms_leaderboard.dto;

import lombok.Data;

/**
 * DTO que representa una marca aprobada de un atleta.
 * Se utiliza para construir el leaderboard a partir de las marcas registradas.
 */
@Data
public class MarcaDTO {

    /**
     * RUT del atleta dueño de la marca.
     */
    private String rutAtleta;

    /**
     * Peso levantado por el atleta en la marca registrada.
     */
    private Double pesoLevantado;
}
