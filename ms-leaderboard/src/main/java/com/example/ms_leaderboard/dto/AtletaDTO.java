package com.example.ms_leaderboard.dto;

import lombok.Data;

/**
 * DTO que representa la información básica de un atleta.
 * Se utiliza para recibir datos desde el microservicio de atletas.
 */
@Data
public class AtletaDTO {

    /**
     * RUT identificador del atleta.
     */
    private String rut;

    /**
     * Nombre del atleta.
     */
    private String nombre;

}
