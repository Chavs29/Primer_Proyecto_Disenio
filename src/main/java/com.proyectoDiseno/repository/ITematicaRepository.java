package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Tematica;
import java.util.List; // Importa la clase List

public interface ITematicaRepository {
    List<Tematica> findAll(String correo); // Define el método findAll para obtener todas las temáticas
    void save(Tematica tematica, String correo);
}
