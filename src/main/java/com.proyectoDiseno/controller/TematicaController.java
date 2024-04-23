package com.proyectoDiseno.controller;
import com.proyectoDiseno.servicios_externos.ConexionIA;
import com.proyectoDiseno.model.ServiceResponse;
import com.proyectoDiseno.model.Tematica;
import com.proyectoDiseno.service.ITematicaService;
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
            long id = System.currentTimeMillis();
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

        guardarRespuestaEnArchivo(respuesta, "respuestaChatGPT.txt");

        ServiceResponse response = new ServiceResponse();
        response.setSuccess(true);
        response.setMessage(respuesta);
        return response;
    }

    private void guardarRespuestaEnArchivo(String respuesta, String nombreArchivo) {
        String rutaProyecto = System.getProperty("user.dir");

        String rutaArchivo = rutaProyecto + "/src/main/resources/templates/" + nombreArchivo;

        try {
            FileWriter escritor = new FileWriter(rutaArchivo);
            escritor.write(respuesta);
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