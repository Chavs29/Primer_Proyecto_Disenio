package com.proyectoDiseno.Model;


import java.util.Date;

public interface ITexto {
    Long getId();
    void setId(Long id);
    String getContenido();
    void setContenido(String contenido);
    Date getFechaRegistro();
    void setFechaRegistro(Date fechaRegistro);
    int getCantidadPalabras();
    void setCantidadPalabras(int cantidadPalabras);
    Tematica getTematica();
    void setTematica(Tematica tematica);
}
