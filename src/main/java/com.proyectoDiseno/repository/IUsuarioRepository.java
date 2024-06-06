package com.proyectoDiseno.repository;

import com.proyectoDiseno.model.Usuario;

import java.util.List;

public interface IUsuarioRepository {

    public List<Usuario> getUsuarios();
    public List<Usuario> findUsuario(String correo);
}

