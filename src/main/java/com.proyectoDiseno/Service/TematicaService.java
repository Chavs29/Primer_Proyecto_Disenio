package com.proyectoDiseno.Service;

import com.proyectoDiseno.Model.Tematica;
import com.proyectoDiseno.Repository.ITematicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; // Importa la clase List

@Service
public class TematicaService implements ITematicaService {
    @Autowired
    private ITematicaRepository tematicaRepository;

    @Override
    public List<Tematica> obtenerTodasLasTematicas(String correo) {
        return tematicaRepository.findAll(correo);
    }

    @Override
    public void crearTematica(Tematica tematica, String correo) {
        tematicaRepository.save(tematica, correo);
    }
}
