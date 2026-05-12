package com.example.ms_verificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.service.VerificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verificaciones")
@RequiredArgsConstructor
public class VerificacionController {

    private final VerificacionService verificacionService;

    @PostMapping("/solicitar")
    public ResponseEntity<Verificacion> crearSolicitud(@Valid @RequestBody Verificacion verificacion) {
        verificacion.setId(null);
        Verificacion nuevaVerificacion = verificacionService.solicitarVerificacion(verificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVerificacion);
    }

    @PutMapping("/{id}/revisar")
    public ResponseEntity<Verificacion> revisarSolicitud(
            @PathVariable Long id, 
            @Valid @RequestBody com.example.ms_verificaciones.dto.RevisionRequest request) {
        
        Verificacion verificacionActualizada = verificacionService.revisarVerificacion(
                id, 
                request.getNuevoEstado(),
                request.getRutValidador()
        );
        
        return ResponseEntity.ok(verificacionActualizada);
    }

    @GetMapping
    public ResponseEntity<List<Verificacion>> listarVerificaciones(){
        return ResponseEntity.ok(verificacionService.listarVerificaciones());
    }
}
