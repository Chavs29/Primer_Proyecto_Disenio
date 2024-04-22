package com.proyectoDiseno.Controller;
import com.proyectoDiseno.ConexionIA.ConexionIA;
import com.proyectoDiseno.Model.ServiceResponse;
import com.proyectoDiseno.Model.Tematica;
import com.proyectoDiseno.Service.ITematicaService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/Tematica")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class TematicaController {
    @Autowired
    private ITematicaService tematicaService;

    @PostMapping("/crear")
    public ResponseEntity<ServiceResponse> crearTematica(@RequestParam("nombre") String nombre,
                                                         @RequestParam("descripcion") String descripcion,
                                                         @RequestParam("imagen") MultipartFile imagen,
                                                         @RequestParam("emailUsuario") String email) {
        ServiceResponse response = new ServiceResponse();
        try {
            byte[] imagenBytes = imagen.getBytes();
            Tematica tematica = new Tematica(nombre, descripcion, imagenBytes);
            long id = System.currentTimeMillis(); // Generar ID como un long
            tematica.setId(id);
            tematicaService.crearTematica(tematica, email);
            response.setSuccess(true);
            response.setMessage("Tematica creada exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setSuccess(false);
            response.setMessage("Error al crear la Tematica: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/lista")
    public ResponseEntity<List<Tematica>> obtenerTodasTematicas(String correo) {
        List<Tematica> tematicas = tematicaService.obtenerTodasLasTematicas(correo);
        return new ResponseEntity<>(tematicas, HttpStatus.OK);
    }
    @PostMapping("/consultaChatGPT")
    public ServiceResponse consultarChatGPT(@RequestBody Map<String, String> body) {
        String texto = body.get("texto");
        String respuesta = ConexionIA.chatGPT(texto);

        // Guardar la respuesta en un archivo
        guardarRespuestaEnArchivo(respuesta, "respuestaChatGPT.txt");

        ServiceResponse response = new ServiceResponse();
        response.setSuccess(true);
        response.setMessage(respuesta);
        return response;
    }

    private void guardarRespuestaEnArchivo(String respuesta, String nombreArchivo) {
        // Obtener la ruta del proyecto
        String rutaProyecto = System.getProperty("user.dir");

        // Ruta relativa donde se guardará el archivo dentro del proyecto
        String rutaArchivo = rutaProyecto + "/src/main/resources/templates/" + nombreArchivo;

        try {
            // Crear un objeto FileWriter
            FileWriter escritor = new FileWriter(rutaArchivo);

            // Escribir el contenido de la respuesta en el archivo
            escritor.write(respuesta);

            // Cerrar el escritor
            escritor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/consultaPalabrasClave")
    public ResponseEntity<ServiceResponse> consultarPalabrasClave(@RequestBody Map<String, Object> requestBody) {
        ServiceResponse response = new ServiceResponse();
        try {
            String texto = (String) requestBody.get("texto");
            String pregunta = "Cuáles son las palabra clave en el texto, hazlo solamente separados por comas: " + texto;
            String respuesta = ConexionIA.chatGPT(pregunta);
            guardarRespuestaEnArchivo(respuesta, "respuestaPalabrasClave.txt");
            response.setSuccess(true);
            response.setMessage(respuesta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al consultar ChatGPT: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/consultaSentimiento")
    public ResponseEntity<ServiceResponse> realizarConsultaSentimiento(@RequestBody Map<String, Object> requestBody) {
        ServiceResponse response = new ServiceResponse();
        try {
            String texto = (String) requestBody.get("texto");
            String pregunta = "Cual es el sentimiento de stanford del texto:" + texto + "Simplemente dame el sentimiento, no digas nada mas(positivo, negativo o neutral).";
            String respuesta = ConexionIA.chatGPT(pregunta);

            guardarRespuestaEnArchivo(respuesta, "respuestaSentimiento.txt");
            response.setSuccess(true);
            response.setMessage(respuesta);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al consultar ChatGPT: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}





}