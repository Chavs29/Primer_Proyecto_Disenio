let listaTextos = [];
document.addEventListener('DOMContentLoaded', function() {
    const opciones = document.getElementById('opciones');
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
                    const primeras30Palabras = obtenerPrimeras30Palabras(texto.contenido);
                    option.text = primeras30Palabras;
                    option.value = texto.id;
                    opcionesTexto.appendChild(option);
                });
            })
            .catch(error => console.error('Error:', error));
    });

}

function obtenerPrimeras30Palabras(texto) {
    const palabras = texto.split(' ');
    const primeras30Palabras = palabras.slice(0, 30);
    return primeras30Palabras.join(' ');
}

function obtenerTextosYActualizarFormulario(textoSeleccionado) {
    console.log('Función ejecutada con valor:', textoSeleccionado);
    const idSeleccionado = parseInt(textoSeleccionado, 10);

    fetch('http://localhost:9090/api/v1/Texto/todos')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la solicitud de datos.');
        }
        return response.json();
    })
    .then(data => {
        const textoEncontrado = data.find(texto => texto.id === idSeleccionado);
        if (textoEncontrado) {
            document.getElementById('id').value = textoEncontrado.id;
            document.getElementById('tematica').value = obtenerTextoSeleccionado2();
            document.getElementById('fecha').value = textoEncontrado.fechaRegistro;
            document.getElementById('cant').value = textoEncontrado.cantidadPalabras;
            document.getElementById('cont').value = textoEncontrado.contenido;
        } else {
            console.error('El texto seleccionado no se encontró en los datos obtenidos.');
        }
    })
    .catch(error => console.error('Error al obtener textos:', error));
}


function guardarDatosEnServidor() {
    // Obtener los valores de los campos del formulario
    var id = document.getElementById('id').value;
    var tematica = document.getElementById('tematica').value;
    var fecha = document.getElementById('fecha').value;
    var cantPalabras = document.getElementById('cant').value;
    var contenido = document.getElementById('cont').value;

    var datos = {
        "id": id,
        "tematica": tematica,
        "fechaRegistro": fecha,
        "cantPalabras": cantPalabras,
        "contenido": contenido
    };

    fetch('http://localhost:9090/api/v1/Consultas/guardarDatos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
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
    var url = 'http://localhost:9090/api/v1/Consultas/consultaChatGPT';
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
    var textoSelect= document.getElementById('cont').value;
    var url = 'http://localhost:9090/api/v1/Consultas/consultaSentimiento';
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
    var textoSelect= document.getElementById('cont').value;
    var url = 'http://localhost:9090/api/v1/Consultas/consultaPalabrasClave';
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

async function generarWordCloud() {
    var textoSelect= document.getElementById('cont').value;
    var url = 'http://localhost:9090/api/v1/WordCloud/generar';
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

async function generarAudio() {
    var textoSelect= document.getElementById('cont').value;
    var url = 'http://localhost:9090/api/v1/Audio/generar';
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
async function verWordCloud() {
    try {
        const response = await fetch('imagenWordCloud.png');
        if (response.ok) {
            const blob = await response.blob();
            const imageUrl = URL.createObjectURL(blob);
            const ventanaEmergente = window.open("", "Imagen", "width=500,height=500");
            const imagen = new Image();
            imagen.src = imageUrl;
            imagen.onload = function() {
                ventanaEmergente.document.body.appendChild(imagen);
            };
        } else {
            console.log('La imagen no se encontró o no se pudo cargar.');
        }
    } catch (error) {
        console.error('Error al cargar la imagen:', error);
    }
}

async function mostrarAudio() {
    try {
        const url = 'audio_generado.mp3';
        const audio = new Audio(url);

        const confirmacion = confirm('¿Desea reproducir el audio?');

        if (confirmacion) {
            audio.play();
        } else {
            console.log('El usuario canceló la reproducción.');
        }
    } catch (error) {
        console.error('Error al cargar el archivo de audio:', error);
    }
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

async function enviarPDF() {
    var textoSelect= localStorage.getItem('correoo');
    var url = 'http://localhost:9090/api/v1/PDF/enviarPDFCorreo';
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
        console.log(data);
    })
    .catch(error => {
        console.error('Error en la solicitud:', error);
    });
}

function mostrarResultado(resultado) {
    const ventanaEmergente = window.open('', '_blank', 'width=600,height=400');
    ventanaEmergente.document.write(`<html><head><title>Resultado</title></head><body><h1>Palabras Clave Generadas:</h1><p>${resultado}</p></body></html>`);
}

