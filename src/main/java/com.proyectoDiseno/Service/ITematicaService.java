package com.proyectoDiseno.Service;

import com.proyectoDiseno.Model.Tematica;
import java.util.List;

public interface ITematicaService {
    void crearTematica(Tematica tematica);
    List<Tematica> obtenerTodasLasTematicas();
}