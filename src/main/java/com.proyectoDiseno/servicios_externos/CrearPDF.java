package com.proyectoDiseno.servicios_externos;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CrearPDF {

    public static byte[] generarPDF(ArrayList<String> contenido) {
        Document documento = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(documento, outputStream);
            documento.open();

            agregarContenido(documento, contenido);

            File imagenFile = new File("/Users/ISAIM/IdeaProjects/Primer_Proyecto_Disenio/src/main/resources/templates/imagenWordCloud.png");
            if (imagenFile.exists()) {
                Image imagen = Image.getInstance(imagenFile.getAbsolutePath());
                documento.add(imagen);
            } else {
                System.err.println("No se encontró la imagen 'imagen.png'.");
            }
            documento.close();
            System.out.println("PDF generado exitosamente");
            return outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void agregarContenido(Document documento, ArrayList<String> contenido) throws DocumentException {
        for (String linea : contenido) {
            documento.add(new Paragraph(linea));
        }
    }
}
