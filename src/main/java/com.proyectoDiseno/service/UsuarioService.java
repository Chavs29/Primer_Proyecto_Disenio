package com.proyectoDiseno.service;

import com.proyectoDiseno.model.Usuario;
import com.proyectoDiseno.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService implements IUsuarioService{
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> lista ;
        try {
            lista = iUsuarioRepository.getUsuarios();
        }
        catch (Exception ex) {
            throw ex;
        }
        return lista;
    }
    @Override
    public List<Usuario> getUsuario(String correo) {
        List<Usuario> lista ;
        try {
            lista = iUsuarioRepository.findUsuario(correo);
        }
        catch (Exception ex) {
            throw ex;
        }
        return lista;
    }

}
