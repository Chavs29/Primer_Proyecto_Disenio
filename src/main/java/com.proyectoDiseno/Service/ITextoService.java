package com.proyectoDiseno.Service;

import com.proyectoDiseno.Model.Texto;
import java.util.List;

public interface ITextoService {
    void crearTexto(Texto texto, String nombre);
    List<Texto> obtenerTodosLosTextos();
    List<Texto> getTextosByTematicaId(String id);
}