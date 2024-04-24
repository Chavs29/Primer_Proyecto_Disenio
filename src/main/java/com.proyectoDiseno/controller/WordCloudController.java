package com.proyectoDiseno.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyectoDiseno.service.WordCloudService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/WordCloud")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class WordCloudController {

    @Autowired
    private WordCloudService wordCloudService;

    @PostMapping("/generar")
    public ResponseEntity<String> generarWordCloud(@RequestBody Map<String, String> body) {
        String resultado = wordCloudService.generarWordCloud(body.get("texto"));
        return ResponseEntity.ok(resultado);
    }
}
