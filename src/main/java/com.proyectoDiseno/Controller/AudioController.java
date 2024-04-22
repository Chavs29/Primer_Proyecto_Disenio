package com.proyectoDiseno.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyectoDiseno.Service.AudioService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/Audio")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class AudioController {

    @Autowired
    private AudioService audioService;

    @PostMapping("/generar") // Cambiado a @PostMapping
    public ResponseEntity<String> generarCodigoQR(@RequestBody Map<String, String> body) {
        String resultado = audioService.generarAudio(body.get("texto"));
        return ResponseEntity.ok(resultado);
    }
}