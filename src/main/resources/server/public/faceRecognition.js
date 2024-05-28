import * as faceapi from 'https://cdn.jsdelivr.net/npm/face-api.js@0.22.2/+esm';
import axios from 'https://cdn.jsdelivr.net/npm/axios@1.6.8/+esm';

document.addEventListener('DOMContentLoaded', function () {
    const loginButton = document.getElementById('loginButton');
    const closeModalButton = document.getElementById('closeModal');
    const retryButton = document.getElementById('retryButton');

    loginButton.onclick = function() {
        document.getElementById('modal').classList.remove('hidden');
        initializeCamera();
    };

    closeModalButton.onclick = function() {
        document.getElementById('modal').classList.add('hidden');
        stopVideoStream();
    };

    retryButton.onclick = function() {
        captureAndCompare();
    };
});

async function initializeCamera() {
    const video = document.getElementById('video');

    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        document.getElementById('errorMessage').textContent = "La cámara no es compatible con este navegador.";
        return;
    }

    try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        video.srcObject = stream;
        video.onloadedmetadata = async () => {
            await loadFaceDetectionModel();
            captureAndCompare();
        };
    } catch (error) {
        document.getElementById('errorMessage').textContent = "No se puede acceder a la cámara.";
    }
}

async function loadFaceDetectionModel() {
    try {
        await faceapi.nets.tinyFaceDetector.loadFromUri('/models');
        await faceapi.nets.faceRecognitionNet.loadFromUri('/models');
        await faceapi.nets.faceLandmark68Net.loadFromUri('/models');
        await faceapi.nets.ssdMobilenetv1.loadFromUri('/models');
    } catch (error) {
        document.getElementById('errorMessage').textContent = "No se pudieron cargar los modelos de detección.";
    }
}


/*
async function obtenerNombreUsuario() {
    try {
        const response = await axios.get('http://localhost:8080/proyecto1Diseno/SvUsuario');
        console.log("Respuesta del servidor:", response.data); // Verificar la respuesta del servidor
        if (response.status === 200) {
            const nombre = response.data.nombre;
            return nombre;
        } else {
            throw new Error('Error al obtener el nombre de usuario. Código de estado: ' + response.status);
        }
    } catch (error) {
        console.error('Error al obtener el nombre de usuario:', error);
        return null;
    }
}
*/

async function captureAndCompare() {
    const video = document.getElementById('video');
    const detection = await faceapi.detectSingleFace(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceDescriptor();
    if (detection) {
        const canvas = faceapi.createCanvasFromMedia(video);
        document.body.append(canvas);
        const base64Image = canvas.toDataURL('image/jpeg');
        console.log(base64Image);

        const capturedDescriptor = detection.descriptor;
        compareWithStoredImages(capturedDescriptor, base64Image);
    } else {
        document.getElementById('errorMessage').textContent = "No se detectó ningún rostro. Intenta de nuevo.";
    }
}


async function compareWithStoredImages(capturedDescriptor, base64Image) {
    try {
        const response = await axios.get('/imagenes-list');
        const imageList = response.data;

        if (!Array.isArray(imageList) || imageList.length === 0) {
            throw new Error("La lista de imágenes está vacía o no es válida");
        }

        let matchFound = false;
        let matchedImage = null;

        for (const imagePath of imageList) {
            const image = await faceapi.fetchImage(imagePath);
            const detections = await faceapi.detectSingleFace(image).withFaceLandmarks().withFaceDescriptor();

            if (detections) {
                const storedDescriptor = detections.descriptor;
                const distance = faceapi.euclideanDistance(capturedDescriptor, storedDescriptor);

                if (distance < 0.6) {  // Umbral para considerar una coincidencia
                    matchFound = true;
                    matchedImage = imagePath;
                    break;
                }
            }
        }

        if (matchFound) {
            console.log("Resultado del match: "+true);
            console.log("ruta: "+matchedImage);
            window.location.href = "http://127.0.0.1:5500/src/main/resources/templates/menu.html";
            // Espera la respuesta de obtenerNombreUsuario()
            //const nombreUsuario = await obtenerNombreUsuario();

            //await sendResponseToBackEnd({ match: true, imagePath: matchedImage, nombreDeUsuario: nombreUsuario });

        } else {
            alert("Usted no está registrado en el sistema");
            //await sendResponseToBackEnd({ match: false, imagePath: "false", nombreDeUsuario: "false" });
        }

    } catch (error) {
        document.getElementById('errorMessage').textContent = "Error al comparar las imágenes.";
        console.error(error);
    }
}



/*
async function sendResponseToBackEnd(response) {
    try {
        const res = await axios.post('http://localhost:8080/proyecto1Diseno/SvFacialRecognition', response);
        if (res.status === 200) {

            const data = res.data;
            const redirectUrl = data.redirectUrl;
        }
    } catch (error) {
        console.error('Error during authentication:', error);
    }
}
*/

function stopVideoStream() {
    const video = document.getElementById('video');
    if (video.srcObject) {
        video.srcObject.getTracks().forEach(track => track.stop());
    }
}