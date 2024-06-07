package com.proyectoDiseno.servicios_externos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GestionarArchivos implements IArchivoLectura{

    private static final Map<Integer, Function<String, String>> PREFIX_MAP = new HashMap<>();

    static {
        PREFIX_MAP.put(0, line -> "Respuesta chatGPT: " + line);
        PREFIX_MAP.put(1, line -> "Palabras clave: " + line);
        PREFIX_MAP.put(2, line -> "Sentimiento según Stanford: " + line);
    }

    public  ArrayList<String> leerTxtFiles(String[] fileNames) {
        ArrayList<String> dataList = new ArrayList<>();

        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            Function<String, String> prefixFunction = PREFIX_MAP.getOrDefault(i, line -> "" + line);
            dataList.addAll(leerArchivoConPrefijo(fileName, prefixFunction));
        }
        return dataList;
    }

    private static ArrayList<String> leerArchivoConPrefijo(String fileName, Function<String, String> prefixFunction) {
        ArrayList<String> dataList = new ArrayList<>();
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(prefixFunction.apply(line));
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + fileName);
        }
        return dataList;
    }
}
