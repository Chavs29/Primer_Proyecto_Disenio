package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Usuario;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setIdentificacion(rs.getString("identificacion"));
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setEmail(rs.getString("email"));
        usuario.setNumeroTelefono(rs.getString("numero_telefono"));
        usuario.setFoto(rs.getBytes("foto"));
        return usuario;
    }
}
