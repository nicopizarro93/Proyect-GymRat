package com.example.ms_marcas.dto;

import com.example.ms_marcas.model.EstadoEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoUpdateRequestDTO {
    @NotNull(message = "El estado no puede estar vacío")
    private EstadoEnum nuevoEstado;
}
