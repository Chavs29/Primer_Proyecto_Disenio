document.addEventListener('DOMContentLoaded', function() {
    const tablaUsuarios = document.getElementById('tablaTematicas');

    fetch('http://localhost:9090/api/v1/Usuario/list')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('tbodyUsuarios');
            data.forEach(usuario => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${usuario.id}</td>
                    <td>${usuario.nombre}</td>
                    <td>${usuario.descripcion}</td>
                    <td><img class="fotoTematicas" src="" alt="Foto de Tematicas"></td>
                `;
                tbody.appendChild(tr);
                const fotoTematicas = tr.querySelector('.fotoUsuario');
                obtenerImagen(usuario.foto, fotoUsuario);
            });
        })
        .catch(error => console.error('Error:', error));
});

function obtenerImagen(fotoBinaria, elementoImagen) {
    elementoImagen.src = `data:image/jpeg;base64, ${fotoBinaria}`;
}
