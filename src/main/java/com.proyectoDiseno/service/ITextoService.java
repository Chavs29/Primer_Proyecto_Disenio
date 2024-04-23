package com.proyectoDiseno.service;

import com.proyectoDiseno.model.Texto;
import java.util.List;

public interface ITextoService {
    void crearTexto(Texto texto, String nombre);
    List<Texto> obtenerTodosLosTextos();
    List<Texto> obtenerTexto(String texto);
    List<Texto> getTextosByTematicaId(String id);
}