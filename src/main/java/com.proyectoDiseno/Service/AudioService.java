package com.proyectoDiseno.Service;

import org.springframework.stereotype.Service;
import com.proyectoDiseno.Audio.AudioGoogle;

@Service
public class AudioService {
    public String generarAudio(String texto) {;
        try {
            AudioGoogle.crearAudio(texto);
            return "Audio generado exitosamente";
        } catch (Exception e) {
            return "Error al generar el audio";
        }
    }
}