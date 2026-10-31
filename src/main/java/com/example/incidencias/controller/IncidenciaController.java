
package com.example.incidencias.controller;

import com.example.incidencias.dto.CrearIncidenciaRequest;
import com.example.incidencias.dto.CambiarEstadoRequest;
import com.example.incidencias.model.Estado;
import com.example.incidencias.model.Incidencia;
import com.example.incidencias.service.IncidenciaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    // Listar todas las incidencias o filtrar por estado
    @GetMapping
    public List<Incidencia> listarIncidencias(
            @RequestParam(required = false) Estado estado) {

        return incidenciaService.listarIncidencias(estado);
    }

    @PostMapping
    public ResponseEntity<Incidencia> crearIncidencia(
            @RequestBody CrearIncidenciaRequest request) {

        // Pasar los datos recibidos al Service
        Incidencia incidencia = incidenciaService.crearIncidencia(
                request.titulo(),
                request.descripcion(),
                request.prioridad(),
                request.usuarioId()
        );

        // Devolver la incidencia creada con HTTP 201
        return ResponseEntity.status(201).body(incidencia);
    }

    // Cambiar el estado de una incidencia
    @PatchMapping("/{id}/estado")
    public Incidencia cambiarEstado(
            @PathVariable Long id,
            @RequestBody CambiarEstadoRequest request) {

        return incidenciaService.cambiarEstado(id, request.estado());
    }
}



