package com.proyectoDiseno.CrearPDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CrearPDF {

    public CrearPDF(){
    }
    public byte[] generarPDF(ArrayList<String> contenido) {
        // Crear documento PDF
        Document documento = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(documento, outputStream);
            documento.open();//se abre para escribir
            for (String linea : contenido) {//se agrega lo del array
                documento.add(new Paragraph(linea));
            }
            documento.close();

            System.out.println("PDF generado exitosamente");

            // Hacer el pdf como un arreglo de bytes
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
     }
}
}
}