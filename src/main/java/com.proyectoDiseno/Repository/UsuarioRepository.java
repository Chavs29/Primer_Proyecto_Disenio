package com.proyectoDiseno.Repository;


import com.proyectoDiseno.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public int save (Usuario usuario) {
        return 0;
    }

}