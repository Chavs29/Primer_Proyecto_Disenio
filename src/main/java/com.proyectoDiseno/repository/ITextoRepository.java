package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Texto;

import java.util.List;

public interface ITextoRepository {
    List<Texto> findAll(); // Obtener todos los textos
    List<Texto> findTexto(String texto);
    List<Texto> findByTematica(String nombre); // Obtener textos por tematica
    void save(Texto texto, String nombre_tematica);
}