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
    const form = document.getElementById('formRegistroTexto');
    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault();

            const temaSeleccionado = obtenerTextoSeleccionado(); // Aquí puedes obtener la temática seleccionada si lo necesitas
            const contenido = document.getElementById('contenido').value;

            const requestData = {
                contenido: contenido,
                nombre: temaSeleccionado
            };

            fetch('http://localhost:9090/api/v1/Texto/crear', {
                method: 'POST',
                body: JSON.stringify(requestData), // Convertir datos a formato JSON
                headers: {
                    'Content-Type': 'application/json', // Usar application/json
                    'Accept': 'application/json',
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.success) {
                        escribirBitacora();
                        alert('Texto registrado exitosamente.');
                        form.reset();
                    } else {
                        alert('Error al registrar el Texto: ' + data.message);
                    }
                })
                .catch(error => console.error('Error:', error));
        });
    } else {
        console.error('Elemento form no encontrado en el DOM.');
    }

    function obtenerTextoSeleccionado() {
        var opcionesTexto = document.getElementById("opciones");
        var textoSeleccionado = opciones.options[opcionesTexto.selectedIndex].text;
        return textoSeleccionado;
    }

    async function escribirBitacora() {
        const datos = {
            accion: "Registro de Texto",
            usuario: correo
        };

        try {
            const response = await fetch('http://localhost:9090/api/v1/bitacora/escribir', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datos)
            });

            if (!response.ok) {
                throw new Error('Error al realizar la solicitud: ' + response.statusText);
            }

            const data = await response.text(); // Si el controlador devuelve un string
            console.log(data); // Manejar la respuesta del backend
        } catch (error) {
            console.error('Error:', error);
        }
    }
});
