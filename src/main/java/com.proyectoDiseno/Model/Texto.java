package com.proyectoDiseno.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Texto {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String contenido;
    @Getter
    @Setter
    private Date fechaRegistro;
    @Getter
    @Setter
    private int cantidadPalabras;
    @Getter
    @Setter
    private Tematica tematica; // Relación con Tematica

    public Texto() {

    }

    public Texto(String contenido, Tematica tematica) {
        this.contenido = contenido;
        this.tematica = tematica;
        this.fechaRegistro = new Date(); // Fecha actual
        this.cantidadPalabras = contarPalabras(contenido);
    }

    public Texto(String contenido, String nombre) {
        this.contenido = contenido;
        this.fechaRegistro = new Date(); // Fecha actual
        this.cantidadPalabras = contarPalabras(contenido);
    }

    // Método para contar palabras en el texto
    private int contarPalabras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return 0;
        }
        String[] palabras = texto.split("\\s+");
        return palabras.length;
    }

}