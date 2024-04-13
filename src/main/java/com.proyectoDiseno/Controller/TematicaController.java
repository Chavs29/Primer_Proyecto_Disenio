package com.proyectoDiseno.Controller;

import com.proyectoDiseno.Model.ServiceResponse;
import com.proyectoDiseno.Model.Tematica;
import com.proyectoDiseno.Service.ITematicaService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Tematica")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class TematicaController {
    @Autowired
    private ITematicaService tematicaService;

    @PostMapping("/crear")
    public ResponseEntity<ServiceResponse> crearTematica(@RequestParam("nombre") String nombre,
                                                         @RequestParam("descripcion") String descripcion,
                                                         @RequestParam("imagen") MultipartFile imagen) {
        ServiceResponse response = new ServiceResponse();
        try {
            byte[] imagenBytes = imagen.getBytes();
            Tematica tematica = new Tematica(nombre, descripcion, imagenBytes);
            tematicaService.crearTematica(tematica);
            response.setSuccess(true);
            response.setMessage("Tematica creada exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al crear la Tematica: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

