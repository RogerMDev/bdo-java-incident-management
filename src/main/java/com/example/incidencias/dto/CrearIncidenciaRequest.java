
package com.example.incidencias.dto;

import com.example.incidencias.model.Prioridad;

public record CrearIncidenciaRequest(
        String titulo,
        String descripcion,
        Prioridad prioridad,
        Long usuarioId
) {
}
