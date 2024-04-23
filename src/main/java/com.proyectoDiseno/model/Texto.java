package com.proyectoDiseno.model;


import java.util.Date;


public class Texto implements ITexto{
    private Long id;
    private String contenido;
    private Date fechaRegistro;
    private int cantidadPalabras;
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

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getCantidadPalabras() {
        return cantidadPalabras;
    }

    public void setCantidadPalabras(int cantidadPalabras) {
        this.cantidadPalabras = cantidadPalabras;
    }

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }
}
