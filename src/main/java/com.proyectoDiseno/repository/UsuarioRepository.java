package com.proyectoDiseno.repository;


import com.proyectoDiseno.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository implements IUsuarioRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Usuario> getUsuarios() {
        String SQL = "SELECT * FROM Usuarios";

        return jdbcTemplate.query(SQL, new UsuarioRowMapper());

    }
    @Override
    public List<Usuario> findUsuario(String correo) {
        String SQL = "SELECT * FROM Usuarios WHERE email = ?";
        return jdbcTemplate.query(SQL, new Object[]{correo}, new UsuarioRowMapper());
    }
    public int save (Usuario usuario) {
        return 0;
    }

}