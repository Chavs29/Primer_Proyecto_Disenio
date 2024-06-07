package com.proyectoDiseno.controller;

import com.proyectoDiseno.model.Usuario;
import com.proyectoDiseno.service.*;
import com.proyectoDiseno.servicios_externos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.proyectoDiseno.validacion_datos.ValidarCorreo.verificacionDeEmail;

@RestController
@RequestMapping("/api/v1/PDF")
@CrossOrigin(origins = {"http://localhost:9090", "http://127.0.0.1:5500"})
public class PDFController {
    private static final Map<Integer, Function<String, String>> PREFIX_MAP = new HashMap<>();
    @Autowired
    private BitacoraService bitacoraService;
    static {
        PREFIX_MAP.put(0, line -> ": " + line);
        PREFIX_MAP.put(1, line -> ": " + line);
        PREFIX_MAP.put(2, line -> ": " + line);
        PREFIX_MAP.put(3, line -> ": " + line);
    }

    @Autowired
    private CrearPDFService crearPDFService;
    @Autowired
    private GestionarArchivosService gestionarArchivosService;
    @Autowired
    private EnviarCorreoService enviarCorreoService;
    @Autowired
    private IUsuarioService iUsuarioService;

    @PostMapping("/enviarPDFCorreo")
    public ResponseEntity<String> generarPDF(@RequestBody Map<String, String> body) {
        String rutaProyecto = System.getProperty("user.dir");
        String asunto = "Datos de Inteligencias JIMAYE";
        String correo = body.get("texto");
        String idioma = body.get("idioma");
        String[] filesNames = {
                rutaProyecto + "/src/main/resources/templates/respuestaChatGPT.txt",
                rutaProyecto + "/src/main/resources/templates/respuestaPalabrasClave.txt",
                rutaProyecto + "/src/main/resources/templates/respuestaSentimiento.txt",
                rutaProyecto + "/src/main/resources/templates/datosTexto.txt"
        };
        ArrayList<String> data1 = gestionarArchivosService.leerTxt(filesNames);
        ArrayList<String> data2 = traducirAIdioma(data1, idioma);
        ArrayList<String> data3 = datosUsuario(correo);
        ArrayList<String> dataFinal = unirArrayLists(data1, data2, data3);
        byte[] pdfBytes = crearPDFService.crearPDF(dataFinal);

        if (verificacionDeEmail(correo)) {
            System.out.println("El correo " + correo + " es válido.");
        } else {
            System.out.println("El correo " + correo + " es inválido.");
        }
        String usuario = body.get("usuario");
        bitacoraService.escribirBitacoras("Consulta de Generar Audio", BitacoraController.obtenerIP(), BitacoraController.obtenerSistemaOperativo(), BitacoraController.obtenerPais(BitacoraController.obtenerIP()), obtenerFechaHora(), usuario);
        enviarCorreoService.enviarEmail(correo, asunto, pdfBytes);
        return ResponseEntity.ok("");
    }

    private ArrayList<String> traducirAIdioma(ArrayList<String> dataList, String idioma) {
        ArrayList<String> translatedList = new ArrayList<>();
        StringBuilder restOfLines = new StringBuilder();


        for (int i = 0; i < dataList.size(); i++) {
            restOfLines.append(dataList.get(i));
        }

        String combinedLines = restOfLines.toString();
        translatedList.add(traducir(combinedLines, idioma));
        return translatedList;
    }

    private String traducir(String texto, String idioma) {
        System.out.println(texto);

        String pregunta = "Te daré un texto, es bastante largo, el texto incluye cantidad de palabras, palabras clave, etc, por favor traducelo absolutamente todo completo y no lo cortes, al idioma " + idioma + ". Este es el texto: "+texto;
        ConexionIA conexionIA = new ConexionIA();
        String respuesta = conexionIA.chatGPT(pregunta);
        System.out.println(pregunta);

        System.out.println(respuesta);

        return respuesta;
    }

    private ArrayList<String> unirArrayLists(ArrayList<String> list1, ArrayList<String> list2, ArrayList<String> list3) {
        ArrayList<String> mergedList = new ArrayList<>();
        mergedList.addAll(list1);
        mergedList.addAll(list2);
        mergedList.addAll(list3);

        return mergedList;
    }

    private ArrayList<String> datosUsuario(String correo) {
        List<Usuario> usuarios = iUsuarioService.getUsuario(correo);
        ArrayList<String> datosFormateados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            datosFormateados.add(applyPrefix(0, usuario.getIdentificacion()));
            datosFormateados.add(applyPrefix(1, usuario.getNombreCompleto()));
            datosFormateados.add(applyPrefix(2, usuario.getEmail()));
            datosFormateados.add(applyPrefix(3, usuario.getNumeroTelefono()));
        }

        return datosFormateados;
    }

    private String applyPrefix(int key, String value) {
        Function<String, String> function = PREFIX_MAP.get(key);
        if (function != null) {
            return function.apply(value);
        } else {
            throw new IllegalArgumentException("No prefix mapping found for key: " + key);
        }
    }

    protected String obtenerFechaHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Costa_Rica")); // Establecer la zona horaria a Costa Rica
        return sdf.format(new Date());
    }

}
