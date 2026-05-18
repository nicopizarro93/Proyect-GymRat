package com.gymrat.ms_atletas.dto;

import com.gymrat.ms_atletas.model.RolEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO utilizado para recibir los datos necesarios
 * al crear un atleta desde una petición HTTP.
 *
 * Este objeto permite validar los datos antes de convertirlos
 * en una entidad Atleta.
 */
@Data
public class AtletaRequestDTO {

    /**
     * RUT del atleta.
     * Es obligatorio y debe cumplir el formato chileno sin puntos y con guion.
     */
    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^\\d{7,8}-[0-9Kk]$", message = "El RUT debe tener el formato 12345678-9 (sin puntos y con guion)")
    private String rut;

    /**
     * Nombre del atleta.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /**
     * Correo electrónico del atleta.
     * Debe tener formato válido y no puede estar vacío.
     */
    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    /**
     * Rol asignado al atleta.
     * Puede ser MIEMBRO o STAFF.
     */
    @NotNull(message = "Debe asignar un rol al atleta")
    private RolEnum rol;
}
