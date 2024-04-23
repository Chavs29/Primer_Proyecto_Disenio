package com.proyectoDiseno.service;

import com.proyectoDiseno.model.Tematica;
import java.util.List;

public interface ITematicaService {
    void crearTematica(Tematica tematica, String correo);
    List<Tematica> obtenerTodasLasTematicas(String correo);
}
