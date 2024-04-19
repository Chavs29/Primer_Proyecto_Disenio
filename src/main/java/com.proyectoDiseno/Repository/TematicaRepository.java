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
    public List<Tematica> findAll(String correo) {
        // Implementa la lógica para obtener todas las temáticas asociadas a un correo específico desde la base de datos
        String SQL = "SELECT * FROM Tematicas WHERE emailUsuario = ?";
        return jdbcTemplate.query(SQL, new Object[]{correo}, new TematicaRowMapper());
    }


    @Override
    public void save(Tematica tematica, String correo) {
        String SQL = "INSERT INTO Tematicas (nombre, descripcion, fotoRepresentativa, emailUsuario) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(SQL, tematica.getNombre(), tematica.getDescripcion(), tematica.getImagen(), correo);
    }
}
