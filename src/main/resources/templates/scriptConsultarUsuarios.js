let funcionesCargadas = false;

document.addEventListener('DOMContentLoaded', function() {
    if (!funcionesCargadas) {
        cargarFunciones();
        funcionesCargadas = true;
    }
});

async function cargarFunciones() {
    const tablaUsuarios = document.getElementById('tablaUsuarios');
    const correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage

    try {
        const response = await fetch('http://localhost:9090/api/v1/Usuario/list');
        if (!response.ok) {
            throw new Error('Error al obtener la lista de usuarios: ' + response.statusText);
        }
        const data = await response.json();
        const tbody = document.getElementById('tbodyUsuarios');
        data.forEach(usuario => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${usuario.id}</td>
                <td>${usuario.identificacion}</td>
                <td>${usuario.nombre_completo}</td>
                <td>${usuario.email}</td>
                <td>${usuario.numero_telefono}</td>
                <td><img class="fotoUsuario" src="" alt="Foto de usuario"></td>
            `;
            tbody.appendChild(tr);
            const fotoUsuario = tr.querySelector('.fotoUsuario');
            obtenerImagen(usuario.foto, fotoUsuario);
        });
    } catch (error) {
        console.error('Error al cargar la lista de usuarios:', error);
    }
}

function escribirBitacora() {
    const correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage
    const datos = {
        accion: "Consulta de Usuarios",
        usuario: correo
    };

    try {
        const response =  fetch('http://localhost:9090/api/v1/bitacora/escribir', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        if (!response.ok) {
            throw new Error('Error al realizar la solicitud: ' + response.statusText);
        }

        const data =  response.text(); // Si el controlador devuelve un string
        console.log(data); // Manejar la respuesta del backend
    } catch (error) {
        console.error('Error al escribir en la bitácora:', error);
    }
}

function obtenerImagen(fotoBinaria, elementoImagen) {
    elementoImagen.src = `data:image/jpeg;base64, ${fotoBinaria}`;
}
