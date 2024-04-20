package com.proyectoDiseno.Repository;

import com.proyectoDiseno.Model.Texto;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TextoRowMapper implements RowMapper<Texto> {
    @Override
    public Texto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Texto texto = new Texto();
        texto.setId(rs.getLong("id"));
        texto.setContenido(rs.getString("contenido"));
        texto.setFechaRegistro(rs.getDate("fecha_registro"));
        texto.setCantidadPalabras(rs.getInt("cantidad_palabras"));
        // No es necesario mapear la temática aquí
        return texto;
    }
}