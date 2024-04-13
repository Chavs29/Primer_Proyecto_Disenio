package com.proyectoDiseno.Controller;

import com.proyectoDiseno.Model.Usuario;
import com.proyectoDiseno.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("api/v1/Usuario")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class UsuarioController {
    @Autowired
    private IUsuarioService iUsuarioService;

    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> list(){
        var result = iUsuarioService.getUsuarios();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
