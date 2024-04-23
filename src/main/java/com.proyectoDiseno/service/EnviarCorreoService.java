package com.proyectoDiseno.service;

import com.proyectoDiseno.servicios_externos.ConexionEnviarCorreo;
import org.springframework.stereotype.Service;

@Service
public class EnviarCorreoService {
    public  String enviarEmail(String destinatario, String asunto, byte[] pdfBytes) {
        try {
            ConexionEnviarCorreo.enviarCorreo(destinatario, asunto, pdfBytes);
            return "Correo enviado exitosamente";
        } catch (Exception e) {
            return "Error al enviar el correo";
        }
    }
}
