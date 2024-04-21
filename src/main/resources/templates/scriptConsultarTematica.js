document.addEventListener('DOMContentLoaded', function() {
    const opciones = document.getElementById('opciones');
    const opcionesTexto = document.getElementById('opcionesTexto'); // Nuevo comboBox para los textos
    const correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage

    const url = `http://localhost:9090/api/v1/Tematica/lista?correo=${correo}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            data.forEach(tematica => {
                const option = document.createElement('option');
                option.text = tematica.nombre;
                option.value = tematica.id;
                opciones.appendChild(option);
            });
            cargarTextos();
        })
        .catch(error => console.error('Error:', error));
    });

// Función para cargar los textos
function cargarTextos() {
    opciones.addEventListener('change', function() {
        const tematicaId = obtenerTextoSeleccionado2();
        const textosUrl = `http://localhost:9090/api/v1/Texto/tematica?id=` + tematicaId;

        fetch(textosUrl)
            .then(response => response.json())
            .then(textosData => {
                opcionesTexto.innerHTML = '';
                textosData.forEach(texto => {
                    const option = document.createElement('option');
                    option.text = texto.contenido; // Cambiar por el campo correcto del texto
                    option.value = texto.id; // Cambiar por el campo correcto del ID del texto
                    opcionesTexto.appendChild(option);
                });
            })
            .catch(error => console.error('Error:', error));
    });
}
function obtenerTextoSeleccionado() {
    var opcionesTexto = document.getElementById("opcionesTexto");
    var textoSeleccionado = opcionesTexto.options[opcionesTexto.selectedIndex].text;
    return textoSeleccionado;
}

    function obtenerTextoSeleccionado2() {
        var opcionesTexto = document.getElementById("opciones");
        var textoSeleccionado = opciones.options[opcionesTexto.selectedIndex].text;
        return textoSeleccionado;
    }

async function consultarChatGPT() {
    var palabrasClave = localStorage.getItem('palabrasClave');
    console.log('Consultar ChatGPT clicado');
    var textoSelect= palabrasClave;
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

async function consultarSentimiento() {
    console.log('Consultar ChatGPT clicado');
    var textoSelect= obtenerTextoSeleccionado();
    var url = 'http://localhost:9090/api/v1/Tematica/consultaSentimiento';
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
        .then(response=>response.json())
        .then(data=>{
            console.log(data);
        })

}



async function mostrarRespuestaChatGPT() {
    try {
        const response = await fetch('respuestaChatGPT.txt');
        if (response.ok) {
            const contenido = await response.text();
            alert(contenido);
        } else {
            console.log('El archivo respuesta.txt no existe.');
        }
    } catch (error) {
        console.error('Error al cargar el contenido de respuesta.txt:', error);
    }
}

async function mostrarRespuestaSentimiento() {
    try {
        const response = await fetch('respuestaSentimiento.txt');
        if (response.ok) {
            const contenido = await response.text();
            alert(contenido);
        } else {
            console.log('El archivo respuesta.txt no existe.');
        }
    } catch (error) {
        console.error('Error al cargar el contenido de respuesta.txt:', error);
    }
}

async function mostrarRespuestaPalabrasClave() {
    try {
        const response = await fetch('respuestaPalabrasClave.txt');
        if (response.ok) {
            const contenido = await response.text();
            localStorage.setItem('palabrasClave', contenido);
            alert(contenido);
        } else {
            console.log('El archivo respuesta.txt no existe.');
        }
    } catch (error) {
        console.error('Error al cargar el contenido de respuesta.txt:', error);
    }
}


function mostrarResultado(resultado) {
    const ventanaEmergente = window.open('', '_blank', 'width=600,height=400');
    ventanaEmergente.document.write(`<html><head><title>Resultado</title></head><body><h1>Palabras Clave Generadas:</h1><p>${resultado}</p></body></html>`);
}

