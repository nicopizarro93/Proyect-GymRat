package com.example.ms_membresia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_membresia.dto.MembresiaRequestDTO;
import com.example.ms_membresia.model.MembresiaModel;
import com.example.ms_membresia.service.MembresiaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST encargado de exponer los endpoints relacionados con membresías.
 * Recibe las solicitudes HTTP y delega la lógica de negocio al servicio correspondiente.
 */
@RestController 
@RequestMapping("/api/v1/membresias") 
@RequiredArgsConstructor
public class MembresiaController {

    /**
     * Servicio que contiene la lógica de negocio para gestionar membresías.
     */
    private final MembresiaService membresiaService;

    /**
     * Crea una nueva membresía para un atleta.
     *
     * @param dto datos necesarios para contratar el plan.
     * @return membresía creada con estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<MembresiaModel> contratar(@Valid @RequestBody MembresiaRequestDTO dto) {
        MembresiaModel nuevaMembresia = membresiaService.contratarPlan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMembresia);
    }

    /**
     * Obtiene la membresía más reciente de un atleta.
     *
     * @param rutAtleta RUT del atleta.
     * @return membresía actual o más reciente del atleta.
     */
    @GetMapping("/{rutAtleta}/actual")
    public ResponseEntity<MembresiaModel> obtenerActual(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.obtenerMembresiaActual(rutAtleta));
    }

    /**
     * Obtiene el historial completo de membresías de un atleta.
     *
     * @param rutAtleta RUT del atleta.
     * @return lista de membresías asociadas al atleta.
     */
    @GetMapping("/{rutAtleta}/historial")
    public ResponseEntity<List<MembresiaModel>> obtenerHistorial(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.obtenerHistorialPorRut(rutAtleta));
    }

    /**
     * Consulta las asistencias de un atleta desde el contexto de membresías.
     *
     * @param rutAtleta RUT del atleta.
     * @return lista de asistencias obtenidas desde el microservicio de asistencia.
     */
    @GetMapping("/{rutAtleta}/asistencias")
    public ResponseEntity<List<Object>> verAsistencias(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.verAsistenciasDesdeMembresia(rutAtleta));
    }
}