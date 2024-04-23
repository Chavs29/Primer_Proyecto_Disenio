package com.proyectoDiseno.servicios_externos;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


public class ConexionEnviarCorreo {
    private String destinatario;
    private String asunto;
    private byte[] pdfBytes;

    public ConexionEnviarCorreo(String pDestinatario, String pAsunto, byte[] pPdfBytes) {
        this.asunto = pAsunto;
        this.destinatario = pDestinatario;
        this.pdfBytes = pPdfBytes;
        enviarCorreo(destinatario, asunto, pdfBytes);
    }

    public static void enviarCorreo(String destinatario, String asunto, byte[] pdfBytes) {

        String correoRemitente = "proyectodisenio2924@gmail.com";
        String contraseñaRemitente = "htnj agwo pohc ahdp";

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        propiedades.setProperty("mail.smtp.port", "587");
        propiedades.setProperty("mail.smtp.user", correoRemitente);
        propiedades.setProperty("mail.smtp.auth", "true");
        propiedades.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(propiedades);

        try {
            Message mensajeCorreo = new MimeMessage(session);
            mensajeCorreo.setFrom(new InternetAddress(correoRemitente));
            mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensajeCorreo.setSubject(asunto);

            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.setContent(pdfBytes, "application/pdf");
            adjuntoPart.setFileName("archivo_adjunto.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(adjuntoPart);

            mensajeCorreo.setContent(multipart);

            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, contraseñaRemitente);
            t.sendMessage(mensajeCorreo, mensajeCorreo.getAllRecipients());
            t.close();
            System.out.println("Correo enviado exitosamente a: " + destinatario);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public byte[] getPdfBytes() {
        return pdfBytes;
    }
}

