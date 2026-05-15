package com.gymrat.ms_atletas.dto;

import com.gymrat.ms_atletas.model.RolEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AtletaRequestDTO {
    
    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^\\d{7,8}-[0-9Kk]$", message = "El RUT debe tener el formato 12345678-9 (sin puntos y con guion)")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotNull(message = "Debe asignar un rol al atleta")
    private RolEnum rol;
}
