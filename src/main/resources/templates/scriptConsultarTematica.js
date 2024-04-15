document.addEventListener('DOMContentLoaded', function() {
    const opciones = document.getElementById('opciones');

    fetch('http://localhost:9090/api/v1/Tematica/lista')
        .then(response => response.json())
        .then(data => {
            data.forEach(tematica => {
                const option = document.createElement('option');
                option.text = tematica.nombre;
                option.value = tematica.id;
                opciones.appendChild(option);
            });
        })
        .catch(error => console.error('Error:', error));
});

function obtenerTextoSeleccionado() {
    var opcionesTexto = document.getElementById("opcionesTexto");
    var textoSeleccionado = opcionesTexto.options[opcionesTexto.selectedIndex].text;
    return textoSeleccionado;
}

function consultarChatGPT() {
    console.log('Consultar ChatGPT clicado');
    var texto = obtenerTextoSeleccionado();
    var dataToSend = { texto: texto };
    console.log('Datos a enviar:', dataToSend); // Mostrar datos a enviar en la consola
    fetch('http://localhost:9090/api/v1/Tematica/consultaChatGPT', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataToSend)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Respuesta recibida:', data); // Mostrar respuesta recibida en la consola
        mostrarResultado(data.message); // Llamamos a la función para mostrar el resultado
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function mostrarResultado(resultado) {
    console.log('Resultado a mostrar:', resultado); // Verificar resultado a mostrar en la consola
    var resultadoContainer = document.getElementById("resultadoContainer");
    resultadoContainer.value = resultado;
}

