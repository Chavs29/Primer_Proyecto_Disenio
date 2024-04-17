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