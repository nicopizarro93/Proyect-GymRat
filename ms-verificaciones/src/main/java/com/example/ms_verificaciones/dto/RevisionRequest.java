package com.example.ms_verificaciones.dto;

import com.example.ms_verificaciones.model.enums.EstadoValidacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RevisionRequest {

    @NotNull(message = "Debe indicar el nuevo estado (APROBADA o RECHAZADA)")
    private EstadoValidacion nuevoEstado;
    
    @NotBlank(message = "El RUT del validador es obligatorio")
    private String rutValidador;
}
