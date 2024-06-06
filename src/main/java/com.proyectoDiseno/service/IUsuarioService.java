package com.proyectoDiseno.service;

import com.proyectoDiseno.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> getUsuarios();
    public List<Usuario> getUsuario(String correo);
}

