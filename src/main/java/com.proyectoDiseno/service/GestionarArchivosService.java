package com.proyectoDiseno.service;

import com.proyectoDiseno.servicios_externos.GestionarArchivos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GestionarArchivosService {
    public ArrayList<String> leerTxt(String[] fileNames) {
        try {
            ArrayList<String> archivo = GestionarArchivos.leerTxtFiles(fileNames);
            return archivo;
        } catch (Exception e) {
            System.out.println("Error al generar el archivo");
            return null;
        }
    }
}
