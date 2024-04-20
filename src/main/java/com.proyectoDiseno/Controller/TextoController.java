package com.proyectoDiseno.Controller;

import com.proyectoDiseno.Model.Texto;
import com.proyectoDiseno.Service.ITextoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/Texto")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class TextoController {
    @Autowired
    private ITextoService textoService;

    @PostMapping("/crear")
    public ResponseEntity<Void> crearTexto(@RequestBody Texto texto) {
        textoService.crearTexto(texto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Texto>> obtenerTodosLosTextos() {
        List<Texto> textos = textoService.obtenerTodosLosTextos();
        return new ResponseEntity<>(textos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Texto> obtenerTextoPorId(@PathVariable("id") long id) {
        Texto texto = textoService.getTextoById(id);
        if (texto != null) {
            return new ResponseEntity<>(texto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tematica/{id}")
    public ResponseEntity<List<Texto>> obtenerTextosPorTematicaId(@PathVariable("id") long id) {
        List<Texto> textos = textoService.getTextosByTematicaId(id);
        return new ResponseEntity<>(textos, HttpStatus.OK);
    }
}