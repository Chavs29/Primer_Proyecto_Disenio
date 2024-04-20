package com.proyectoDiseno.Model;

import java.util.Date;

public class Texto {
    private Long id;
    private String contenido;
    private Date fechaRegistro;
    private int cantidadPalabras;
    private Tematica tematica; // Relación con Tematica

    public Texto(String contenido, Tematica tematica) {
        this.contenido = contenido;
        this.tematica = tematica;
        this.fechaRegistro = new Date(); // Fecha actual
        this.cantidadPalabras = contarPalabras(contenido);
    }

    public Texto() {

    }

    // Método para contar palabras en el texto
    private int contarPalabras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return 0;
        }
        String[] palabras = texto.split("\\s+");
        return palabras.length;
    }

    public Object getContenido() {
        return  contenido;
    }

    public Object getFechaRegistro() {
        return fechaRegistro;
    }

    public Object getCantidadPalabras() {
        return cantidadPalabras;
    }

    public Tematica getTematica() {
        return tematica;
    }


    public void setId(long id) {
    }

    public void setContenido(String contenido) {
    }

    public void setFechaRegistro(java.sql.Date fechaRegistro) {
    }

    public void setCantidadPalabras(int cantidadPalabras) {
    }
}