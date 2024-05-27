package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Texto;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TextoRowMapper implements RowMapper<Texto> {
    @Override
    public Texto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Texto texto = new Texto();
        texto.setId(rs.getLong("id_texto"));
        texto.setContenido(rs.getString("contenido"));
        texto.setFechaRegistro(rs.getDate("fecha_registro"));
        texto.setCantidadPalabras(rs.getInt("cantidad_palabras"));
        return texto;
    }
}

