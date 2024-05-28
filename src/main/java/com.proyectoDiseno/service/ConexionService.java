package com.proyectoDiseno.service;

import com.proyectoDiseno.servicios_externos.ConexionIA;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConexionService {
    public  String generarConexionIA(String texto){
        try {
            String respuesta = ConexionIA.chatGPT(texto);
            return respuesta;
        } catch (Exception e) {
            System.out.println("Error al realizar la consulta");
        }
        return "";
    }

}
