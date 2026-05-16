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

@RestController 
@RequestMapping("/api/v1/membresias") 
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    // REST Puro: POST a /membresias crea un recurso
    @PostMapping
    public ResponseEntity<MembresiaModel> contratar(@Valid @RequestBody MembresiaRequestDTO dto) {
        // Le pasamos la pelota inmediatamente al Service (Controlador Delgado)
        MembresiaModel nuevaMembresia = membresiaService.contratarPlan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMembresia);
    }

    @GetMapping("/{rutAtleta}/actual")
    public ResponseEntity<MembresiaModel> obtenerActual(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.obtenerMembresiaActual(rutAtleta));
    }

    @GetMapping("/{rutAtleta}/historial")
    public ResponseEntity<List<MembresiaModel>> obtenerHistorial(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.obtenerHistorialPorRut(rutAtleta));
    }

    @GetMapping("/{rutAtleta}/asistencias")
    public ResponseEntity<List<Object>> verAsistencias(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.verAsistenciasDesdeMembresia(rutAtleta));
    }
}