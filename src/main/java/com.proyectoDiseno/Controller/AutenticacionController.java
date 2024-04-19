package com.proyectoDiseno.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyectoDiseno.Service.AutenticacionService;

@RestController
@RequestMapping("/api/v1/Autenticar")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    @PostMapping("/generarCodigoQR") // Cambiado a @PostMapping
    public ResponseEntity<String> generarCodigoQR(@RequestParam String cuenta) {
        String resultado = autenticacionService.generarCodigoQR(cuenta);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/verificarCodigo") // Cambiado a @PostMapping
    public ResponseEntity<String> verificarCodigo(@RequestParam String cuenta, @RequestParam String codigo) {
        String resultado = autenticacionService.verificarCodigo(cuenta, codigo);
        return ResponseEntity.ok(resultado);
    }
}
