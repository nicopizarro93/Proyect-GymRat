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

/**
 * Controlador REST encargado de exponer los endpoints relacionados con las marcas.
 * Permite registrar, consultar, listar y actualizar el estado de las marcas de los atletas.
 */
@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    /**
     * Registra una nueva marca enviada por un atleta.
     *
     * @param dto datos necesarios para registrar la marca.
     * @return marca creada con estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Marca> registrarMarca(@Valid @RequestBody MarcaRequestDTO dto) {
        Marca nuevaMarca = marcaService.registrarIntento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    /**
     * Obtiene todas las marcas asociadas a un atleta según su RUT.
     *
     * @param rut RUT del atleta.
     * @return lista de marcas del atleta.
     */
    @GetMapping("/atleta/{rut}")
    public ResponseEntity<List<Marca>> obtenerMarcasDeAtleta(@PathVariable String rut) {
        return ResponseEntity.ok(marcaService.obtenerPorRut(rut));
    }

    /**
     * Lista todas las marcas registradas en el sistema.
     *
     * @return lista completa de marcas.
     */
    @GetMapping
    public ResponseEntity<List<Marca>> listarTodasLasMarcas() {
        return ResponseEntity.ok(marcaService.listarTodos());
    }

    /**
     * Busca una marca específica por su identificador.
     *
     * @param id identificador de la marca.
     * @return marca encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    /**
     * Obtiene las marcas aprobadas de un ejercicio específico.
     *
     * @param nombreEjercicio nombre del ejercicio consultado.
     * @return lista de marcas aprobadas ordenadas por peso levantado.
     */
    @GetMapping("/ejercicio/{nombreEjercicio}/aprobadas")
    public ResponseEntity<List<Marca>> obtenerMarcasAprobadas(@PathVariable String nombreEjercicio) {
        return ResponseEntity.ok(marcaService.obtenerMarcasAprobadasPorEjercicio(nombreEjercicio));
    }

    /**
     * Actualiza el estado de una marca existente.
     *
     * @param id identificador de la marca.
     * @param request DTO con el nuevo estado de la marca.
     * @return marca actualizada.
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<Marca> actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoUpdateRequestDTO request) {
        return ResponseEntity.ok(marcaService.actualizarEstado(id, request.getNuevoEstado()));
    }
}