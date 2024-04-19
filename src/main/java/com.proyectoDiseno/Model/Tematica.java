package com.proyectoDiseno.Model;

public class Tematica implements ITematica {
    private long id;
    private String nombre;
    private String descripcion;
    private byte[] imagen;
    private Usuario usuario; // Campo que representa la relación con Usuario

    public Tematica() {
        // Constructor vacío necesario para crear instancias sin proporcionar un id
    }

    public Tematica(String nombre, String descripcion, byte[] imagen, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.usuario = usuario;
    }
    public Tematica(String nombre, String descripcion, byte[] imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
