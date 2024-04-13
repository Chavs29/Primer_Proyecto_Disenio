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
            `;
            tbody.appendChild(tr);
        });
    })
    .catch(error => console.error('Error:', error));
