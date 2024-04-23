package com.proyectoDiseno.service;

import com.proyectoDiseno.servicios_externos.ConexionEnviarCorreo;
import com.proyectoDiseno.validacion_datos.ValidarCorreo;
import org.springframework.stereotype.Service;

@Service
public class EnviarCorreoService {
    public String enviarEmail(String destinatario, String asunto, byte[] pdfBytes) {
        // Verificar la validez del correo electrónico
        boolean correoValido = ValidarCorreo.verificacionDeEmail(destinatario);

        if (correoValido) {
            try {
                ConexionEnviarCorreo.enviarCorreo(destinatario, asunto, pdfBytes);
                return "Correo enviado exitosamente";
            } catch (Exception e) {
                return "Error al enviar el correo";
            }
        } else {
            return "El correo electrónico proporcionado no es válido";
        }
    }
}
