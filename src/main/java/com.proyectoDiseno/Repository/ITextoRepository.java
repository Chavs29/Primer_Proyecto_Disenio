package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Texto;
import com.proyectoDiseno.Model.Tematica;
import java.util.List;

public interface ITextoRepository {
    List<Texto> findAll(); // Obtener todos los textos
    List<Texto> findByTematica(Tematica tematica); // Obtener textos por tematica
    void save(Texto texto);

    Texto findById(long id);

    List<Texto> findByTematicaId(long id);
}