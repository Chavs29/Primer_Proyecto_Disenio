package com.proyectoDiseno.controller;
import com.proyectoDiseno.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyectoDiseno.service.WordCloudService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/v1/WordCloud")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class WordCloudController {

    @Autowired
    private WordCloudService wordCloudService;
    @Autowired
    private BitacoraService bitacoraService;

    @PostMapping("/generar")
    public ResponseEntity<String> generarWordCloud(@RequestBody Map<String, String> body) {
        String resultado = wordCloudService.generarWordCloud(body.get("texto"));
        String usuario = body.get("usuario");
        bitacoraService.escribirBitacoras("Consulta de Generar WordCloud", BitacoraController.obtenerIP(), BitacoraController.obtenerSistemaOperativo(), BitacoraController.obtenerPais(BitacoraController.obtenerIP()), obtenerFechaHora(), usuario);
        return ResponseEntity.ok(resultado);
    }
    protected String obtenerFechaHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Establecer la zona horaria si es necesario
        return sdf.format(new Date());
    }
}
