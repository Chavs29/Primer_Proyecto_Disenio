document.getElementById("formularioCorreo").addEventListener("submit", async function(event) {
    event.preventDefault();
    const correo = document.getElementById("correo").value;
    const endpoint = "http://localhost:9090/api/v1/Usuario/list";

    try {
        const response = await fetch(endpoint);
        const data = await response.json();

        let correoEncontrado = false;
        for (const usuario of data) {
            if (usuario.email === correo) {
                correoEncontrado = true;
                break;
            }
        }

        if (correoEncontrado) {
            alert("El correo existe en la base de datos.");
            // Redirigir a IniciarSesion.html si el correo existe
            window.location.href = "IniciarSesion.html";
        } else {
            alert("El correo no existe en la base de datos.");
        }
    } catch (error) {
        console.error("Error al validar el correo:", error);
        alert("Hubo un error al validar el correo.");
    }
});