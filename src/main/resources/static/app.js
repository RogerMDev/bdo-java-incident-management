
const filtroEstado = document.getElementById("filtroEstado");
const tablaIncidencias = document.getElementById("tablaIncidencias");
const mensajeError = document.getElementById("mensajeError");

// Consultar incidencias desde nuestra API REST
async function cargarIncidencias() {
    const estado = filtroEstado.value;

    let url = "/api/incidencias";

    // Agregar el filtro solo si hay un estado seleccionado
    if (estado !== "") {
        url += "?estado=" + estado;
    }

    mensajeError.hidden = true;
    tablaIncidencias.replaceChildren();

    try {
        const respuesta = await fetch(url);

        if (!respuesta.ok) {
            throw new Error("No se pudieron cargar las incidencias");
        }

        const incidencias = await respuesta.json();

        incidencias.forEach(incidencia => {
            const fila = document.createElement("tr");

            const valores = [
                incidencia.id,
                incidencia.titulo,
                incidencia.prioridad,
                incidencia.estado,
                incidencia.usuarioAsignado?.nombre ?? "Sin asignar"
            ];

            valores.forEach(valor => {
                const celda = document.createElement("td");
                celda.textContent = valor;
                fila.appendChild(celda);
            });

            tablaIncidencias.appendChild(fila);
        });

    } catch (error) {
        mensajeError.textContent = error.message;
        mensajeError.hidden = false;
    }
}

// Cambiar el filtro sin recargar la pagina
filtroEstado.addEventListener("change", cargarIncidencias);

// Cargar las incidencias al abrir la pagina
cargarIncidencias();
