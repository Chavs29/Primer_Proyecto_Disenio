package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Tematica;
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
        // Si el campo imagen es de tipo BLOB o VARBINARY, deberías manejarlo aquí
        tematica.setImagen(rs.getBytes("fotoRepresentativa"));
        return tematica;
    }
}
