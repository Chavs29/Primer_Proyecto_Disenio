package com.proyectoDiseno.controller;
import com.proyectoDiseno.servicios_externos.ConexionIA;
import com.proyectoDiseno.model.ServiceResponse;
import com.proyectoDiseno.model.Tematica;
import com.proyectoDiseno.service.ITematicaService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/Tematica")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class TematicaController {
    @Autowired
    private ITematicaService tematicaService;

    @PostMapping("/crear")
    public ResponseEntity<ServiceResponse> crearTematica(@RequestParam("nombre") String nombre,
                                                         @RequestParam("descripcion") String descripcion,
                                                         @RequestParam("imagen") MultipartFile imagen,
                                                         @RequestParam("emailUsuario") String email) {
        ServiceResponse response = new ServiceResponse();
        try {
            byte[] imagenBytes = imagen.getBytes();
            Tematica tematica = new Tematica(nombre, descripcion, imagenBytes);
            long id = System.currentTimeMillis();
            tematica.setId(id);
            tematicaService.crearTematica(tematica, email);
            response.setSuccess(true);
            response.setMessage("Tematica creada exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setSuccess(false);
            response.setMessage("Error al crear la Tematica: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/lista")
    public ResponseEntity<List<Tematica>> obtenerTodasTematicas(String correo) {
        List<Tematica> tematicas = tematicaService.obtenerTodasLasTematicas(correo);

        List<Tematica> tematicasOrdenadas = tematicas.stream()
                .sorted(Comparator.comparing(Tematica::getNombre))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tematicasOrdenadas, HttpStatus.OK);
    }

}