package com.proyectoDiseno.controller;

import com.proyectoDiseno.service.CrearPDFService;
import com.proyectoDiseno.service.EnviarCorreoService;
import com.proyectoDiseno.service.GestionarArchivosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/PDF")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class PDFController {

    @Autowired
    private CrearPDFService crearPDFService;
    @Autowired
    private GestionarArchivosService gestionarArchivosService;
    @Autowired
    private EnviarCorreoService enviarCorreoService;

    @PostMapping("/enviarPDFCorreo") // Cambiado a @PostMapping
    public ResponseEntity<String> generarPDF(@RequestBody Map<String, String> body) {

        String rutaProyecto = System.getProperty("user.dir");

        String[] filesNames = {rutaProyecto + "/src/main/resources/templates/respuestaChatGPT.txt", rutaProyecto + "/src/main/resources/templates/respuestaPalabrasClave.txt",rutaProyecto + "/src/main/resources/templates/respuestaSentimiento.txt"};
        ArrayList<String> data = gestionarArchivosService.leerTxt(filesNames);
        byte[] pdfBytes = crearPDFService.crearPDF(data);
        String asunto = "Datos de Inteligencias JIMAYE";
        String correo = body.get("texto");
        System.out.println(correo);
        enviarCorreoService.enviarEmail(correo, asunto, pdfBytes);
        return ResponseEntity.ok("");
    }
}




