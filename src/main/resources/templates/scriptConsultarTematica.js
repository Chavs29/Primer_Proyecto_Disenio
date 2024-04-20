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
    var textoSelect= obtenerTextoSeleccionado();
    var url = 'http://localhost:9090/api/v1/Tematica/consultaChatGPT';
    console.log('URL de la solicitud:', url);
    var datos = {
        texto: textoSelect
    }
    fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        })
        .then(response=>response.json())
        .then(data=>{
            console.log(data);
        })

}

async function generarPalabrasClave() {
    console.log('Consultar ChatGPT clicado');
    var textoSelect= obtenerTextoSeleccionado();
    var url = 'http://localhost:9090/api/v1/Tematica/consultaPalabrasClave';
    console.log('URL de la solicitud:', url);
    var datos = {
        texto: textoSelect
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
        })
        .then(response => response.json())
        .then(data => {
           debugger
        })

}


function mostrarResultado(resultado) {
    const ventanaEmergente = window.open('', '_blank', 'width=600,height=400');
    ventanaEmergente.document.write(`<html><head><title>Resultado</title></head><body><h1>Palabras Clave Generadas:</h1><p>${resultado}</p></body></html>`);
}
