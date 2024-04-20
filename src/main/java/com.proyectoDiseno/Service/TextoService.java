package com.proyectoDiseno.Service;

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
    public void crearTexto(Texto texto) {
        textoRepository.save(texto);
    }

    @Override
    public List<Texto> obtenerTodosLosTextos() {
        return textoRepository.findAll();
    }

    @Override
    public Texto getTextoById(long id) {
        return textoRepository.findById(id);
    }

    @Override
    public List<Texto> getTextosByTematicaId(long id) {
        return textoRepository.findByTematicaId(id);
}
}