package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Texto;
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
        String SQL = "SELECT * FROM Textos";
        return jdbcTemplate.query(SQL, new TextoRowMapper());
    }

    @Override
    public List<Texto> findTexto(String texto) {
        String SQL = "SELECT * FROM Textos WHERE contenido = ?";
        return jdbcTemplate.query(SQL, new Object[]{texto}, new TextoRowMapper());
    }

    @Override
    public List<Texto> findByTematica(String nombre) {
        String SQL = "SELECT * FROM Textos WHERE id_tematica = ?";
        return jdbcTemplate.query(SQL, new Object[]{nombre}, new TextoRowMapper());
    }


    @Override
    public void save(Texto texto, String nombre) {
        String SQL = "INSERT INTO Textos (contenido, fecha_registro, cantidad_palabras, id_tematica) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(SQL, texto.getContenido(), texto.getFechaRegistro(), texto.getCantidadPalabras(),nombre);
    }
}