package com.gymrat.ms_atletas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Atleta> crearAtleta(@Valid @RequestBody Atleta atleta) {
        Atleta nuevoAtleta = atletaService.guardarAtleta(atleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAtleta);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Atleta> obtenerPorRut(@PathVariable String rut) {
        Atleta atleta = atletaService.buscarPorRut(rut);
        return ResponseEntity.ok(atleta);
    }

    @GetMapping
    public ResponseEntity<?> listarAtletas() {
        List<Atleta> atletas = atletaService.listarTodos();
        return ResponseEntity.ok(atletas);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> elminarAtleta(@PathVariable String rut) {
        atletaService.eliminarPorRut(rut);
        return ResponseEntity.noContent().build();
    }

}
