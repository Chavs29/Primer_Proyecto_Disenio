document.addEventListener('DOMContentLoaded', function() {
    // Llenar las opciones al cargar el DOM
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
        })
        .catch(error => console.error('Error:', error));

    // Enviar el formulario al hacer submit
    const formularioRegistroTexto = document.getElementById('formularioRegistroTexto');
    if (formularioRegistroTexto) {
        formularioRegistroTexto.addEventListener('submit', function(event) {
            event.preventDefault();

            const temaSeleccionado = document.getElementById('opciones').value;
            const texto = document.getElementById('textoTematica').value;

            const formData = new FormData();
            formData.append('contenido', texto);
            formData.append('nombre', temaSeleccionado);

            fetch('http://localhost:9090/api/v1/Texto/crear', {
                method: 'POST',
                body: formData,
                headers: {
                    'Accept': 'application/json',
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.success) {
                        alert('Texto registrado exitosamente.');
                        formularioRegistroTexto.reset();
                    } else {
                        alert('Error al registrar el Texto: ' + data.message);
                    }
                })
                .catch(error => console.error('Error:', error));
        });
    } else {
        console.error('Elemento formularioRegistroTexto no encontrado en el DOM.');
    }
});
