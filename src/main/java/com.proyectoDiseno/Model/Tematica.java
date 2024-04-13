package com.proyectoDiseno.Model;

public class Tematica implements ITematica{
    private String nombre;
    private String descripcion;
    private byte[] imagen;

    public Tematica(String nombre, String descripcion, byte[] imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public byte[] getImagen() {
        return imagen;
    }
}
