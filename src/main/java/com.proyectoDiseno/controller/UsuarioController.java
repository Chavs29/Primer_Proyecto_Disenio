package com.proyectoDiseno.controller;

import com.proyectoDiseno.model.Usuario;
import com.proyectoDiseno.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/Usuario")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class UsuarioController {
    @Autowired
    private IUsuarioService iUsuarioService;

    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> usuarios = iUsuarioService.getUsuarios();

        // Ordena la lista de usuarios alfabéticamente por el nombre
        List<Usuario> usuariosOrdenados = usuarios.stream()
                .sorted(Comparator.comparing(Usuario::getNombreCompleto))
                .collect(Collectors.toList());

        return new ResponseEntity<>(usuariosOrdenados, HttpStatus.OK);
    }
}
