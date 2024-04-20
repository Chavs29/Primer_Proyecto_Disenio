fetch('http://localhost:9090/api/v1/Usuario/list')
    .then(response => response.json())
    .then(data => {
        const tbody = document.getElementById('tbodyUsuarios');
        data.forEach(usuario => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${usuario.id}</td>
                <td>${usuario.identificacion}</td>
                <td>${usuario.nombre_completo}</td>
                <td>${usuario.email}</td>
                <td>${usuario.numero_telefono}</td>
                <td><img id="imgUsuario${usuario.id}" alt="Foto Usuario"></td>
            `;
            tbody.appendChild(tr);

            const blob = new Blob([usuario.foto], { type: 'image/jpeg' });
            const imageUrl = URL.createObjectURL(blob);

            const imgElement = document.getElementById(`imgUsuario${usuario.id}`);
            imgElement.src = imageUrl;
        });
    })
    .catch(error => console.error('Error:', error));
