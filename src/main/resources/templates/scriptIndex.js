document.getElementById("formularioCorreo").addEventListener("submit", async function(event) {
    event.preventDefault();
    const correo = document.getElementById("correo").value;
    const endpoint = "http://localhost:9090/api/v1/Usuario/list";
    var correo1 = "";
    try {
        const response = await fetch(endpoint);
        const data = await response.json();
        let correoEncontrado = false;
        for (const usuario of data) {
            if (usuario.email === correo) {
                correo1 = usuario.email;
                correoEncontrado = true;
                localStorage.setItem("correoo", correo1);
                break;
            }
        }

        if (correoEncontrado) {
            alert("El correo existe en la base de datos.");
            // Redirigir a IniciarSesion.html si el correo existe
            generarCodigoQR(correo1);
            window.location.href = "http://localhost:3000/";
        } else {
            alert("El correo no existe en la base de datos.");
        }
    } catch (error) {
        console.error("Error al validar el correo:", error);
        alert("Hubo un error al validar el correo.");
    }


    async function generarCodigoQR(correo) {
        const formData = new FormData();
        formData.append('cuenta', correo);

        try {
            const response = await fetch('http://localhost:9090/api/v1/Autenticar/generarCodigoQR', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Error al generar el código QR');
            }

            console.log('Código QR generado exitosamente');
        } catch (error) {
            console.error('Error al generar el código QR:', error);
        }
    }
});