document.addEventListener('DOMContentLoaded', function() {
    const formularioRegistro = document.getElementById('formularioRegistro');
    const correo = localStorage.getItem('correoo'); // Obtener el correo de localStorage
    formularioRegistro.addEventListener('submit', function(event) {
        event.preventDefault();

        const nombreTematica = document.querySelector('input[name="nombreTematica"]').value;
        const descripcionTematica = document.querySelector('input[name="descripcionTematica"]').value;
        const imagenTematica = document.querySelector('input[name="imagenTematica"]').files[0];

        const formData = new FormData();
        formData.append('nombre', nombreTematica);
        formData.append('descripcion', descripcionTematica);
        formData.append('imagen', imagenTematica);
        formData.append('emailUsuario',correo);

        fetch('http://localhost:9090/api/v1/Tematica/crear', {
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
                    escribirBitacora();
                    alert('Tematica registrada exitosamente.');
                    formularioRegistro.reset();
                } else {
                    alert('Error al registrar la Tematica: ' + data.message);
                }
            })
            .catch(error => console.error('Error:', error));
    });
    async function escribirBitacora() {
        const datos = {
            accion: "Registro de temática",
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
