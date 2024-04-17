package com.proyectoDiseno.Model;

import lombok.Data;

@Data
public class Usuario {
    private long id;
    private String identificacion;
    private String nombre_completo;
    private String email;
    private String numero_telefono;
    private byte[] foto;
    public Usuario() {
        // Constructor vacío
    }

    // Getters y setters generados automáticamente por Lombok
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCompleto() {
        return nombre_completo;
    }

    public void setNombreCompleto(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numero_telefono;
    }

    public void setNumeroTelefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public byte[] getFoto() {
        return foto;
    }


    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
