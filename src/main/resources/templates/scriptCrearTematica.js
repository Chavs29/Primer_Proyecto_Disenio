document.addEventListener('DOMContentLoaded', function() {
    const formularioRegistro = document.getElementById('formularioRegistro');

    formularioRegistro.addEventListener('submit', function(event) {
        event.preventDefault();

        const nombreTematica = document.querySelector('input[name="nombreTematica"]').value;
        const descripcionTematica = document.querySelector('input[name="descripcionTematica"]').value;
        const imagenTematica = document.querySelector('input[name="imagenTematica"]').files[0];

        const formData = new FormData();
        formData.append('nombre', nombreTematica);
        formData.append('descripcion', descripcionTematica);
        formData.append('imagen', imagenTematica);

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
                    alert('Tematica registrada exitosamente.');
                    formularioRegistro.reset();
                } else {
                    alert('Error al registrar la Tematica: ' + data.message);
                }
            })
            .catch(error => console.error('Error:', error));
    });
});
