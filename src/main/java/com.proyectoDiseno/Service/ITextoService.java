package com.proyectoDiseno.Service;

import com.proyectoDiseno.Model.Texto;
import java.util.List;

public interface ITextoService {
    void crearTexto(Texto texto);
    List<Texto> obtenerTodosLosTextos();
    Texto getTextoById(long id);
    List<Texto> getTextosByTematicaId(long id);
}