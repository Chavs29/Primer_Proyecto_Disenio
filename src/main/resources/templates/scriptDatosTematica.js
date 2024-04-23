document.addEventListener('DOMContentLoaded', function() {
    const tablaTematicas = document.getElementById('tablaTematicas');
    const correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage
    const url = `http://localhost:9090/api/v1/Tematica/lista?correo=${correo}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('tbodyTematica');
            data.forEach(tematica => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${tematica.id}</td>
                    <td>${tematica.nombre}</td>
                    <td>${tematica.descripcion}</td>
                    <td><img class="fotoTematica" src="" alt="Foto de temática"></td>
                `;
                tbody.appendChild(tr);
                const fotoTematica = tr.querySelector('.fotoTematica');
                obtenerImagen(tematica.imagen, fotoTematica);
            });
        })
        .catch(error => console.error('Error:', error));
});

function obtenerImagen(fotoBinaria, elementoImagen) {
    elementoImagen.src = `data:image/jpeg;base64, ${fotoBinaria}`;
}
