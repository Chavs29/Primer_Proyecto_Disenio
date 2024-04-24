package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Tematica;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TematicaRowMapper implements RowMapper<Tematica> {
    @Override
    public Tematica mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tematica tematica = new Tematica();
        tematica.setId(rs.getLong("id"));
        tematica.setNombre(rs.getString("nombre"));
        tematica.setDescripcion(rs.getString("descripcion"));
        tematica.setImagen(rs.getBytes("fotoRepresentativa"));
        return tematica;
    }
}
