document.addEventListener('DOMContentLoaded', function () {
    // Llenar los combobox con las 24 horas del día
    const horas = [];
    const correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage
    for (let i = 0; i < 24; i++) {
        const hour = i.toString().padStart(2, '0');
        horas.push(hour + ":00");
    }

    const selectHoraInicio = document.getElementById('horaInicio');
    const selectHoraFinal = document.getElementById('horaFinal');

    horas.forEach(hora => {
        const optionInicio = document.createElement('option');
        optionInicio.value = hora;
        optionInicio.text = hora;
        selectHoraInicio.appendChild(optionInicio);

        const optionFinal = document.createElement('option');
        optionFinal.value = hora;
        optionFinal.text = hora;
        selectHoraFinal.appendChild(optionFinal);
    });

    document.getElementById('mostrar').addEventListener('click', showData);
});

function showData() {
    // Obtener los valores seleccionados
    const formatSelect = document.getElementById('format-select1');
    const consultaSelect = document.getElementById('format-select2');
    const usuarioSelect = document.getElementById('format-select3');
    const horaInicioSelect = document.getElementById('horaInicio');
    const horaFinalSelect = document.getElementById('horaFinal');

    const formato = formatSelect.value;
    const consulta = consultaSelect.value;
    const usuario = usuarioSelect.value;
    const horaInicio = horaInicioSelect.value;
    const horaFinal = horaFinalSelect.value;
    var correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage
    let url = `http://localhost:9090/api/v1/bitacora/`;

    switch (consulta) {
        case "1":
            url += `registrosHoyPorUsuario?tipo=${formato}&usuario=${correo}`;
            break;
        case "2":
            url += `registrosPorHora?tipo=${formato}&horaInicio=${horaInicio}&horaFin=${horaFinal}&usuario=${correo}`;
            break;
        case "3":
            url += `consultaDeTodosLosRegistrosPorUsuario?tipo=${formato}&usuario=${usuario}`;
            break;
        case "4":
            url += `todosLosRegistros?tipo=${formato}`;
            break;
    }

    // Hacer la solicitud GET al endpoint
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta de la red');
            }
            return response.json();
        })
        .then(data => {
            // Convertir el array de registros a una cadena de texto
            var registrosTexto = data.join('\n');

            // Mostrar los registros en el textarea
            var registrosTextarea = document.getElementById('registros');
            registrosTextarea.value = registrosTexto;
        })
        .catch(error => {
            console.error('Error al obtener los registros:', error);
        });

}
