package com.proyectoDiseno.service;
import com.proyectoDiseno.servicios_externos.CrearPDF;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CrearPDFService {
    public  byte[] crearPDF(ArrayList<String> contenido){
        try {
            byte[] pdf = CrearPDF.generarPDF(contenido);
            return pdf;
        } catch (Exception e) {
            System.out.println("Error al generar el PDF");
        }
        return new byte[0];
    }

}
