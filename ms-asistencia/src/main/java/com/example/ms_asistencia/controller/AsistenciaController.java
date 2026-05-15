package com.example.ms_asistencia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_asistencia.dto.AsistenciaRequestDTO;
import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.service.AsistenciaService; // Usamos la interfaz

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    // Inyectamos la Interfaz, no la implementación
    private final AsistenciaService asistenciaService;

    @PostMapping
    public ResponseEntity<Asistencia> registrarAsistencia(@Valid @RequestBody AsistenciaRequestDTO request) {
        // La validación del DTO nos asegura que el RUT no viene nulo ni vacío
        Asistencia asistencia = asistenciaService.registrarAsistencia(request.getRutAtleta());
        return ResponseEntity.status(HttpStatus.CREATED).body(asistencia);
    }

    @GetMapping("/historial/{rutAtleta}")
    public ResponseEntity<List<Asistencia>> obtenerHistorial(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasPorRut(rutAtleta));
    }

    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodas() {
        return ResponseEntity.ok(asistenciaService.obtenerTodasAsistencias());
    }
}