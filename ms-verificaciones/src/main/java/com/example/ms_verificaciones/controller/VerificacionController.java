package com.example.ms_verificaciones.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ms_verificaciones.dto.RevisionRequest;
import com.example.ms_verificaciones.dto.VerificacionRequestDTO;
import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.service.VerificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verificaciones")
@RequiredArgsConstructor
public class VerificacionController {

    private final VerificacionService verificacionService;

    // Cambiamos de @PostMapping("/solicitar") a @PostMapping a secas y usamos el DTO
    @PostMapping
    public ResponseEntity<Verificacion> crearSolicitud(@Valid @RequestBody VerificacionRequestDTO dto) {
        Verificacion nuevaVerificacion = verificacionService.solicitarVerificacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVerificacion);
    }

    @PutMapping("/{id}/revisar")
    public ResponseEntity<Verificacion> revisarSolicitud(
            @PathVariable Long id, 
            @Valid @RequestBody RevisionRequest request) {
        
        Verificacion actualizada = verificacionService.revisarVerificacion(
                id, request.getNuevoEstado(), request.getRutValidador()
        );
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping
    public ResponseEntity<List<Verificacion>> listarVerificaciones(){
        return ResponseEntity.ok(verificacionService.listarVerificaciones());
    }
}