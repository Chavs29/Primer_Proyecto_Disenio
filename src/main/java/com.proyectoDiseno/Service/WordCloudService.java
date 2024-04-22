package com.proyectoDiseno.Service;

import org.springframework.stereotype.Service;
import com.proyectoDiseno.WordCloud.CrearWordCloud;

@Service
public class WordCloudService {
    public String generarWordCloud(String texto) {;
        try {
            CrearWordCloud.crearWordCloud(texto);
            return "WordCloud generado exitosamente";
        } catch (Exception e) {
            return "Error al generar el WordCloud";
        }
    }
}
