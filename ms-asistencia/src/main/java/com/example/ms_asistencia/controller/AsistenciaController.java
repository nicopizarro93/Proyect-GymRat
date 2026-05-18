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

/**
 * Controlador REST encargado de exponer los endpoints relacionados
 * con el registro y consulta de asistencias de atletas.
 */
@RestController
@RequestMapping("/api/v1/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    /**
     * Servicio que contiene la lógica de negocio para gestionar asistencias.
     */
    private final AsistenciaService asistenciaService;

    /**
     * Registra una nueva asistencia para un atleta.
     * Valida el RUT recibido y delega al servicio la verificación de acceso.
     *
     * @param request DTO con el RUT del atleta que intenta ingresar.
     * @return asistencia registrada con estado PERMITIDO o DENEGADO.
     */
    @PostMapping
    public ResponseEntity<Asistencia> registrarAsistencia(@Valid @RequestBody AsistenciaRequestDTO request) {
        Asistencia asistencia = asistenciaService.registrarAsistencia(request.getRutAtleta());
        return ResponseEntity.status(HttpStatus.CREATED).body(asistencia);
    }

    /**
     * Obtiene el historial de asistencias de un atleta específico.
     *
     * @param rutAtleta RUT del atleta consultado.
     * @return lista de asistencias asociadas al atleta.
     */
    @GetMapping("/historial/{rutAtleta}")
    public ResponseEntity<List<Asistencia>> obtenerHistorial(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasPorRut(rutAtleta));
    }

    /**
     * Obtiene todas las asistencias registradas en el sistema.
     *
     * @return lista completa de asistencias.
     */
    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodas() {
        return ResponseEntity.ok(asistenciaService.obtenerTodasAsistencias());
    }
}