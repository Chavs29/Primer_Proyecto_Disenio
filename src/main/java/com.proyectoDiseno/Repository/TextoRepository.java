package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Texto;
import com.proyectoDiseno.Model.Tematica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TextoRepository implements ITextoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Texto> findAll() {
        // Implementar lógica para obtener todos los textos desde la base de datos
        String SQL = "SELECT * FROM Textos";
        return jdbcTemplate.query(SQL, new TextoRowMapper());
    }

    @Override
    public List<Texto> findByTematica(Tematica tematica) {
        // Implementar lógica para obtener textos por temática desde la base de datos
        String SQL = "SELECT * FROM Textos WHERE id_tematica = ?";
        return jdbcTemplate.query(SQL, new Object[]{tematica.getId()}, new TextoRowMapper());
    }

    @Override
    public void save(Texto texto) {
        String SQL = "INSERT INTO Textos (contenido, fecha_registro, cantidad_palabras, id_tematica) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(SQL, texto.getContenido(), texto.getFechaRegistro(), texto.getCantidadPalabras(), texto.getTematica().getId());
    }

    @Override
    public Texto findById(long id) {
        return null;
    }

    @Override
    public List<Texto> findByTematicaId(long id) {
        return List.of();
    }
}