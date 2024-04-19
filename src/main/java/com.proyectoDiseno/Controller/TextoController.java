package com.proyectoDiseno.Controller;

import com.proyectoDiseno.Model.Texto;
import com.proyectoDiseno.Service.ITextoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyectoDiseno.Model.ServiceResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/Texto")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class TextoController {
    @Autowired
    private ITextoService textoService;

    @PostMapping("/crear")
    public ResponseEntity<ServiceResponse> crearTexto(@RequestParam("contenido") String contenido,
                                                      @RequestParam("nombre") String nombre) {
        ServiceResponse response = new ServiceResponse();
        try {
            Texto texto = new Texto(contenido, nombre);
            textoService.crearTexto(texto, nombre);
            response.setSuccess(true);
            response.setMessage("Texto creado exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setSuccess(false);
            response.setMessage("Error al crear el Texto: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/todos")
    public ResponseEntity<List<Texto>> obtenerTodosLosTextos() {
        List<Texto> textos = textoService.obtenerTodosLosTextos();
        return new ResponseEntity<>(textos, HttpStatus.OK);
    }


    @GetMapping("/tematica/{id}")
    public ResponseEntity<List<Texto>> obtenerTextosPorTematicaId(@PathVariable("id") String id) {
        List<Texto> textos = textoService.getTextosByTematicaId(id);
        return new ResponseEntity<>(textos, HttpStatus.OK);
    }
    }