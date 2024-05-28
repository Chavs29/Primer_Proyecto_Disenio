package com.proyectoDiseno.controller;

import com.proyectoDiseno.model.ServiceResponse;
import com.proyectoDiseno.service.ConexionService;
import com.proyectoDiseno.servicios_externos.ConexionIA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/Consultas")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class ConsultasExternasController {
    @Autowired
    private ConexionService conexionIA;

    @PostMapping("/consultaChatGPT")
    public ServiceResponse consultarChatGPT(@RequestBody Map<String, String> body) {
        String texto = body.get("texto");
        String respuesta = conexionIA.generarConexionIA(texto);

        guardarRespuestaEnArchivo(respuesta, "respuestaChatGPT.txt");

        ServiceResponse response = new ServiceResponse();
        response.setSuccess(true);
        response.setMessage(respuesta);
        return response;
    }

    @PostMapping("/guardarDatos")
    public ServiceResponse guardarDatos(@RequestBody Map<String, String> body) {
        String texto = "Datos del texto:"+"\n"+"Id:" + body.get("id")+ "\n"+"Tematica:"+body.get("tematica")+ "\n"+"Fecha de Registro:"+body.get("fechaRegistro")+ "\n"+"Cantidad de palabras:"+body.get("cantPalabras")+ "\n"+"Contenido:"+body.get("contenido");

        guardarRespuestaEnArchivo(texto, "datosTexto.txt");
        ServiceResponse response = new ServiceResponse();
        response.setSuccess(true);
        response.setMessage(texto);        return response;
    }

    @PostMapping("/consultaPalabrasClave")
    public ResponseEntity<ServiceResponse> consultarPalabrasClave(@RequestBody Map<String, Object> requestBody) {
        ServiceResponse response = new ServiceResponse();
        try {
            String texto = (String) requestBody.get("texto");
            String pregunta = "Cuáles son las palabra clave en el texto, hazlo solamente separados por comas: " + texto;
            String respuesta = conexionIA.generarConexionIA(pregunta);
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
            String respuesta = conexionIA.generarConexionIA(pregunta);

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
}
