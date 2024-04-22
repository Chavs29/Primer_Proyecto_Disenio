package com.proyectoDiseno.Service;

import com.proyectoDiseno.Model.ITematica;
import com.proyectoDiseno.Model.Texto;
import com.proyectoDiseno.Repository.ITextoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextoService implements ITextoService {
    @Autowired
    private ITextoRepository textoRepository;
    @Override
    public void crearTexto(Texto texto, String nombre) {
        textoRepository.save(texto, nombre);
    }

    @Override
    public List<Texto> obtenerTodosLosTextos() {
        return textoRepository.findAll();
    }

    @Override
    public List<Texto> obtenerTexto(String texto) {
        return textoRepository.findTexto(texto);
    }

    @Override
    public List<Texto> getTextosByTematicaId(String id) {
        return textoRepository.findByTematica(id);
    }
}