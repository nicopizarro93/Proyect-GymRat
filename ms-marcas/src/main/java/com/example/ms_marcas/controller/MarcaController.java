package com.example.ms_marcas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Marca> registrarMarca(@Valid @RequestBody Marca marca) {
        marca.setId(null);
        Marca nuevaMarca = marcaService.registrarIntento(marca);
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

}
