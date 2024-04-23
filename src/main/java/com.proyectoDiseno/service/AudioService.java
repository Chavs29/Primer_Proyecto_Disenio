package com.proyectoDiseno.service;

import org.springframework.stereotype.Service;
import com.proyectoDiseno.servicios_externos.AudioGoogle;

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