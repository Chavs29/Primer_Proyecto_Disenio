package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Texto;

import java.util.List;

public interface ITextoRepository {
    List<Texto> findAll();
    List<Texto> findByTematica(String nombre);
    List<Texto> findTexto(String texto);
    void save(Texto texto, String nombre_tematica);
}