package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Tematica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TematicaRepository implements ITematicaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Tematica> findAll() {
        // Implementa la lógica para obtener todas las temáticas desde la base de datos
        String SQL = "SELECT * FROM Tematicas";
        return jdbcTemplate.query(SQL, new TematicaRowMapper());
    }

    @Override
    public void save(Tematica tematica) {
        String SQL = "INSERT INTO Tematicas (nombre, descripcion, fotoRepresentativa) VALUES (?, ?, ?)";
        jdbcTemplate.update(SQL, tematica.getNombre(), tematica.getDescripcion(), tematica.getImagen());
    }
}
