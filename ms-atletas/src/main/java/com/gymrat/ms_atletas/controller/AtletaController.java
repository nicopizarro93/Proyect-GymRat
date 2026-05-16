package com.gymrat.ms_atletas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gymrat.ms_atletas.dto.AtletaRequestDTO;
import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.services.AtletaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/atletas")
@RequiredArgsConstructor
public class AtletaController {

    private final AtletaService atletaService;

    @PostMapping
    public ResponseEntity<Atleta> crearAtleta(@Valid @RequestBody AtletaRequestDTO dto) {
        // Mapeamos manualmente el DTO a la Entidad
        Atleta nuevoAtleta = new Atleta();
        nuevoAtleta.setRut(dto.getRut());
        nuevoAtleta.setNombre(dto.getNombre());
        nuevoAtleta.setEmail(dto.getEmail());
        nuevoAtleta.setRol(dto.getRol());

        Atleta atletaGuardado = atletaService.guardarAtleta(nuevoAtleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(atletaGuardado);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Atleta> obtenerPorRut(@PathVariable String rut) {
        Atleta atleta = atletaService.buscarPorRut(rut);
        return ResponseEntity.ok(atleta);
    }

    @GetMapping
    public ResponseEntity<List<Atleta>> listarAtletas() {
        // Cambiamos el <?> por <List<Atleta>> para ser más estrictos con lo que devolvemos
        return ResponseEntity.ok(atletaService.listarTodos());
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> elminarAtleta(@PathVariable String rut) {
        atletaService.eliminarPorRut(rut);
        return ResponseEntity.noContent().build();
    }
}
