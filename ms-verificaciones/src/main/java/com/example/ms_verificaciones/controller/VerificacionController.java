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

/**
 * Controlador REST encargado de exponer los endpoints relacionados
 * con las solicitudes de verificación de marcas.
 */
@RestController
@RequestMapping("/api/v1/verificaciones")
@RequiredArgsConstructor
public class VerificacionController {

    /**
     * Servicio que contiene la lógica de negocio para crear,
     * revisar y listar verificaciones.
     */
    private final VerificacionService verificacionService;

    /**
     * Crea una nueva solicitud de verificación de marca.
     *
     * @param dto datos necesarios para registrar la solicitud.
     * @return verificación creada con estado inicial pendiente.
     */
    @PostMapping
    public ResponseEntity<Verificacion> crearSolicitud(@Valid @RequestBody VerificacionRequestDTO dto) {
        Verificacion nuevaVerificacion = verificacionService.solicitarVerificacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVerificacion);
    }

    /**
     * Permite revisar una solicitud de verificación existente,
     * aprobándola o rechazándola según la evaluación recibida.
     *
     * @param id identificador de la solicitud de verificación.
     * @param request datos de revisión, incluyendo estado nuevo y RUT del validador.
     * @return verificación actualizada.
     */
    @PutMapping("/{id}/revisar")
    public ResponseEntity<Verificacion> revisarSolicitud(
            @PathVariable Long id, 
            @Valid @RequestBody RevisionRequest request) {
    
        Verificacion actualizada = verificacionService.revisarVerificacion(
                id, request.getNuevoEstado(), request.getRutValidador()
        );
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Lista todas las solicitudes de verificación registradas.
     *
     * @return listado completo de verificaciones.
     */
    @GetMapping
    public ResponseEntity<List<Verificacion>> listarVerificaciones(){
        return ResponseEntity.ok(verificacionService.listarVerificaciones());
    }
}