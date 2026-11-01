
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



//FORMULARIO DE INCIDENCIAS
const formIncidencia = document.getElementById("formIncidencia");
const mensajeFormulario = document.getElementById("mensajeFormulario");

// Escuchar cuando el usuario envia el formulario
formIncidencia.addEventListener("submit", async function (event) {

    // Evitar que la pagina se recargue
    event.preventDefault();

    // Recoger los datos introducidos
    const usuarioId = document.getElementById("usuarioId").value;

    const nuevaIncidencia = {
        titulo: document.getElementById("titulo").value,
        descripcion: document.getElementById("descripcion").value,
        prioridad: document.getElementById("prioridad").value,
        usuarioId: usuarioId === "" ? null : Number(usuarioId)
    };

    try {
        mensajeFormulario.textContent = "";

        // Enviar los datos al backend
        const respuesta = await fetch("/api/incidencias", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevaIncidencia)
        });

        // Comprobar si la peticion ha fallado
        if (!respuesta.ok) {
            const error = await respuesta.json();
            throw new Error(error.error || "No se pudo crear la incidencia");
        }

        // Mostrar mensaje y limpiar formulario
        mensajeFormulario.textContent = "Incidencia creada correctamente";
        formIncidencia.reset();

        // Actualizar la tabla
        await cargarIncidencias();

    } catch (error) {
        mensajeFormulario.textContent = error.message;
    }
});

