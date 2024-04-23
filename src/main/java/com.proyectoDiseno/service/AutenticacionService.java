package com.proyectoDiseno.service;

import org.springframework.stereotype.Service;
import com.proyectoDiseno.servicios_externos.Autenticar;

@Service
public class AutenticacionService {
    private final String llaveSecreta = Autenticar.generarLlaveSecreta();
    public String generarCodigoQR(String cuenta) {;
        String enlaceCodigo = Autenticar.ObtenerCodigoGoogle(llaveSecreta, cuenta);
        try {
            Autenticar.crearQR(enlaceCodigo);
            return "Código QR generado exitosamente";
        } catch (Exception e) {
            return "Error al generar el código QR";
        }
    }

    public String verificarCodigo(String cuenta, String codigo) {
        String codigoMostrado = Autenticar.obtenerCodigoMostrado(llaveSecreta);
        if (codigo.equals(codigoMostrado)) {
            return "Autenticación exitosa";
        } else {
            return "Código de autenticación inválido";
        }
    }
}
