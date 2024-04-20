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
                                                         @RequestParam("imagen") MultipartFile imagen) {
        ServiceResponse response = new ServiceResponse();
        try {
            byte[] imagenBytes = imagen.getBytes();
            Tematica tematica = new Tematica(nombre, descripcion, imagenBytes);
            long id = System.currentTimeMillis(); // Generar ID como un long
            tematica.setId(id);
            tematicaService.crearTematica(tematica);
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
    public ResponseEntity<List<Tematica>> obtenerTodasTematicas() {
        List<Tematica> tematicas = tematicaService.obtenerTodasLasTematicas();
        return new ResponseEntity<>(tematicas, HttpStatus.OK);
    }

    @PostMapping("/consultaChatGPT")
    public ServiceResponse consultarChatGPT(@RequestBody Map<String, String> body) {
        String texto = body.get("texto");
        String respuesta = ConexionIA.chatGPT(texto);
        System.out.println("Pruebaaa: "+ respuesta);
        ServiceResponse response = new ServiceResponse();
        response.setSuccess(true);
        response.setMessage(respuesta);
        return response;
    }


    @PostMapping("/consultaPalabrasClave")
    public ResponseEntity<ServiceResponse> consultarPalabrasClave(@RequestBody Map<String, Object> requestBody) {
        ServiceResponse response = new ServiceResponse();
        try {
            String texto = (String) requestBody.get("texto");
            String pregunta = "Cuáles son las palabra clave en el texto, hazlo solamente separados por comas: " + texto;
            String respuesta = ConexionIA.chatGPT(pregunta);
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
            String pregunta = "Cual es el sentimiento de stanford del texto:" + texto;
            String respuesta = ConexionIA.chatGPT(pregunta);
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
