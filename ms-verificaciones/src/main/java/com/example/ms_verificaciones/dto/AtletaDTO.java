package com.example.ms_verificaciones.dto;

import lombok.Data;

/**
 * DTO utilizado para recibir información básica de un atleta
 * desde el microservicio ms-atletas.
 */
@Data
public class AtletaDTO {

    /**
     * RUT del atleta consultado.
     */
    private String rut;

    /**
     * Rol del atleta dentro del sistema.
     * Puede ser STAFF o MIEMBRO.
     */
    private String rol;
}
