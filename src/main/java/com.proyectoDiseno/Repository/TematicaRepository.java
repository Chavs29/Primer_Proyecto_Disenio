package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Tematica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TematicaRepository implements ITematicaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Tematica tematica) {
        String SQL = "INSERT INTO Tematicas (nombre, descripcion, fotoRepresentativa) VALUES (?, ?, ?)";
        jdbcTemplate.update(SQL, tematica.getNombre(), tematica.getDescripcion(), tematica.getImagen());
    }
}
