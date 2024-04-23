document.addEventListener('DOMContentLoaded', function() {
    const tablaTematicas = document.getElementById('tablaTematicas');
    const correoT = localStorage.getItem('correoo');
    fetch('http://localhost:9090/api/v1/Tematica/lista?correo='+ correoT)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('tbodyTematica');
            data.forEach(tematica => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${tematica.id}</td>
                    <td>${tematica.nombre}</td>
                    <td>${tematica.descripcion}</td>
                    <td><img class="fotoTematicas" src="" alt="Foto de Tematicas"></td>
                `;
                tbody.appendChild(tr);
                const fotoTematicas = tr.querySelector('.fotoTematica');
                obtenerImagen(tematica.foto, fotoTematica);
            });
        })
        .catch(error => console.error('Error:', error));
});

function obtenerImagen(fotoBinaria, elementoImagen) {
    elementoImagen.src = `data:image/jpeg;base64, ${fotoBinaria}`;
}
