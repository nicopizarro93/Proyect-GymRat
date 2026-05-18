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

/**
 * Controlador REST encargado de exponer los endpoints
 * relacionados con la gestión de atletas.
 */
@RestController
@RequestMapping("/api/v1/atletas")
@RequiredArgsConstructor
public class AtletaController {

    /**
     * Servicio que contiene la lógica de negocio de atletas.
     */
    private final AtletaService atletaService;

    /**
     * Endpoint para crear un nuevo atleta.
     * Recibe un DTO validado desde el cuerpo de la petición.
     */
    @PostMapping
    public ResponseEntity<Atleta> crearAtleta(@Valid @RequestBody AtletaRequestDTO dto) {

        // Se crea una nueva entidad Atleta a partir de los datos recibidos en el DTO.
        Atleta nuevoAtleta = new Atleta();

        // Se asigna el RUT recibido desde la petición.
        nuevoAtleta.setRut(dto.getRut());

        // Se asigna el nombre recibido desde la petición.
        nuevoAtleta.setNombre(dto.getNombre());

        // Se asigna el email recibido desde la petición.
        nuevoAtleta.setEmail(dto.getEmail());

        // Se asigna el rol recibido desde la petición.
        nuevoAtleta.setRol(dto.getRol());

        // Se guarda el atleta usando el servicio.
        Atleta atletaGuardado = atletaService.guardarAtleta(nuevoAtleta);

        // Se responde con estado HTTP 201 CREATED y el atleta guardado.
        return ResponseEntity.status(HttpStatus.CREATED).body(atletaGuardado);
    }

    /**
     * Endpoint para buscar un atleta por su RUT.
     */
    @GetMapping("/{rut}")
    public ResponseEntity<Atleta> obtenerPorRut(@PathVariable String rut) {

        // Busca el atleta según el RUT recibido por la URL.
        Atleta atleta = atletaService.buscarPorRut(rut);

        // Retorna el atleta encontrado con estado HTTP 200 OK.
        return ResponseEntity.ok(atleta);
    }

    /**
     * Endpoint para listar todos los atletas registrados.
     */
    @GetMapping
    public ResponseEntity<List<Atleta>> listarAtletas() {

        // Retorna una lista con todos los atletas existentes.
        return ResponseEntity.ok(atletaService.listarTodos());
    }

    /**
     * Endpoint para eliminar un atleta según su RUT.
     */
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> elminarAtleta(@PathVariable String rut) {

        // Elimina el atleta que tenga el RUT indicado.
        atletaService.eliminarPorRut(rut);

        // Retorna estado HTTP 204 NO CONTENT porque la eliminación fue exitosa.
        return ResponseEntity.noContent().build();
    }
}
