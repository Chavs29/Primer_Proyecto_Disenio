package com.proyectoDiseno.controller;

import com.proyectoDiseno.model.Tematica;
import com.proyectoDiseno.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bitacora")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @PostMapping("/escribir")
    public String escribirBitacora(@RequestBody Map<String, String> body) {
        String accion = body.get("accion");
        String ip = obtenerIP();
        String sistemaOperativo = obtenerSistemaOperativo();
        String pais = obtenerPais(obtenerIP());
        String fechaHora = obtenerFechaHora();
        String usuario = body.get("usuario");

        // Llama al servicio para escribir en todas las bitácoras
        bitacoraService.escribirBitacoras(accion, ip, sistemaOperativo, pais, fechaHora, usuario);

        return "Registro añadido a todas las bitácoras";
    }

    @GetMapping("/todosLosRegistros")
    public ResponseEntity<ArrayList<String>> obtenerTodosLosRegistros(String tipo) {
        ArrayList<String> registros = bitacoraService.consultarDeTodosLosRegistros(tipo);

        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/registrosPorHora")
    public ResponseEntity<ArrayList<String>> obtenerRegistrosPorHora(String tipo, String horaInicio, String horaFin, String usuario) {
        ArrayList<String> registros = bitacoraService.consultaPorHoras(tipo,horaInicio,horaFin, usuario);

        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/registrosHoyPorUsuario")
    public ResponseEntity<ArrayList<String>> obtenerRegistrosHoyPorUsuario(String tipo, String usuario) {
        ArrayList<String> registros = bitacoraService.consultaDeHoyPorUsuario(tipo, usuario);

        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/consultaDeTodosLosRegistrosPorUsuario")
    public ResponseEntity<ArrayList<String>> consultaDeTodosLosRegistrosPorUsuario(String tipo, String usuario) {
        ArrayList<String> registros = bitacoraService.consultaDeTodosLosRegistrosPorUsuario(tipo, usuario);

        return new ResponseEntity<>(registros, HttpStatus.OK);
    }


    protected static String obtenerSistemaOperativo() {
        return System.getProperty("os.name");
    }

    protected static String obtenerIP() {
        String ip = "Unknown";
        try {
            URL url = new URL("https://api.ipify.org?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            ip = jsonObject.get("ip").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }


    protected static String obtenerPais(String ip) {
        String country = "Unknown";
        if ("Unknown".equals(ip)) {
            return country;
        }

        try {
            URL url = new URL("https://ipapi.co/" + ip + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            country = jsonObject.get("country_name").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return country;
    }

    protected String obtenerFechaHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Establecer la zona horaria si es necesario
        return sdf.format(new Date());
    }
}
