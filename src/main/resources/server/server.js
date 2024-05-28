const express = require('express');
const path = require('path');
const fs = require('fs');
const app = express();

// Set headers for all responses
app.use((req, res, next) => {
    res.setHeader("Content-Security-Policy", "script-src 'self' https://cdn.jsdelivr.net;");
    next();
});

// Serve static files with specific headers for JS
app.use(express.static(path.join(__dirname, 'public'), {
    setHeaders: (res, path) => {
        if (path.endsWith('.js')) {
            res.type('application/javascript');
        }
    }
}));

// Directorio donde están las imágenes
const imagesDir = path.join(__dirname, 'public', 'imagenes');
// Verificar la existencia del directorio de imágenes
if (!fs.existsSync(imagesDir)) {
    console.error("El directorio de imágenes no existe.");
} else {
    console.log("Directorio de imágenes encontrado.");
}
// Verificar los archivos dentro del directorio de imágenes
fs.readdir(imagesDir, (err, files) => {
    if (err) {
        console.error("Error al leer el directorio de imágenes:", err);
    } else {
        console.log("Archivos en el directorio de imágenes:", files);
    }
});


// Servir imágenes estáticamente
app.use('/imagenes', express.static(imagesDir));

// Ruta para obtener la lista de imágenes
app.get('/imagenes-list', (req, res) => {
    fs.readdir(imagesDir, (err, files) => {
        if (err) {
            return res.status(500).json({ error: 'No se pueden leer las imágenes' });
        }
        // Filtrar solo archivos con extensiones de imagen
        const imageUrls = files.filter(file => /\.(jpg|jpeg|png|gif)$/i.test(file))
                               .map(file =>` /imagenes/${file}`);
        res.json(imageUrls);
    });
});

// Fallback to serve index.html
app.get('*', (req, res) => {
    res.sendFile(path.resolve(__dirname, 'public', 'index.html'));
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));