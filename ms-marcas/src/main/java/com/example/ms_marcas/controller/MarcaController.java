package com.example.ms_marcas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_marcas.dto.EstadoUpdateRequestDTO;
import com.example.ms_marcas.dto.MarcaRequestDTO;
import com.example.ms_marcas.model.Marca;
import com.example.ms_marcas.services.MarcaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @PostMapping
    public ResponseEntity<Marca> registrarMarca(@Valid @RequestBody MarcaRequestDTO dto) {
        Marca nuevaMarca = marcaService.registrarIntento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    @GetMapping("/atleta/{rut}")
    public ResponseEntity<List<Marca>> obtenerMarcasDeAtleta(@PathVariable String rut) {
        return ResponseEntity.ok(marcaService.obtenerPorRut(rut));
    }

    @GetMapping
    public ResponseEntity<List<Marca>> listarTodasLasMarcas() {
        return ResponseEntity.ok(marcaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @GetMapping("/ejercicio/{nombreEjercicio}/aprobadas")
    public ResponseEntity<List<Marca>> obtenerMarcasAprobadas(@PathVariable String nombreEjercicio) {
        return ResponseEntity.ok(marcaService.obtenerMarcasAprobadasPorEjercicio(nombreEjercicio));
    }

    // 🔴 CAMBIO CLAVE: Usamos el DTO para recibir el nuevo estado en el Body
    @PutMapping("/{id}/estado")
    public ResponseEntity<Marca> actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoUpdateRequestDTO request) {
        return ResponseEntity.ok(marcaService.actualizarEstado(id, request.getNuevoEstado()));
    }
}