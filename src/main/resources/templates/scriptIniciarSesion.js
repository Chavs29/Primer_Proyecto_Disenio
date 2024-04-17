document.addEventListener("DOMContentLoaded", function() {
    const formularioValidacion = document.querySelector('#formularioValidacion');

    formularioValidacion.addEventListener('submit', async function(event) {
        event.preventDefault();
        const codigo = document.querySelector("#codigoInput").value;

        try {
            const resultado = await validarCodigo(codigo);

            // Mostrar el resultado de la validación en algún elemento HTML
            document.querySelector('#resultadoValidacion').textContent = resultado;

            // Si la validación es exitosa, redirigir a menu.html
            if (resultado === "Autenticación exitosa") {
                alert("Autenticación exitosa");
                window.location.href = "menu.html";
            }
        } catch (error) {
            console.error("Error al verificar el código:", error);
            alert("Hubo un error al verificar el código.");
        }
    });
});

async function validarCodigo(codigo) {
    const correo = "marcochaves0229@gmail.com"; // Correo fijo proporcionado
    const formData = new FormData();
    formData.append('cuenta', correo);
    formData.append('codigo', codigo);

    try {
        const response = await fetch('http://localhost:9090/api/v1/Autenticar/verificarCodigo', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error('Error al verificar el código');
        }

        const data = await response.text();
        return data;
    } catch (error) {
        throw new Error('Error al verificar el código');
    }
}
