package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Tematica;
import java.util.List; // Importa la clase List

public interface ITematicaRepository {
    List<Tematica> findAll(); // Define el método findAll para obtener todas las temáticas
    void save(Tematica tematica);
}
