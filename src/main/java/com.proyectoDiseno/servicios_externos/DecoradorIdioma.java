package com.proyectoDiseno.servicios_externos;

import java.util.ArrayList;

public class DecoradorIdioma extends DecoradorArchivoLectura {
    public DecoradorIdioma(IArchivoLectura archivoLectura){
        super(archivoLectura);
    }

    @Override
    public ArrayList<String> leerTxtFiles(String[] filesNames){
        ArrayList<String> dataList = super.leerTxtFiles(filesNames);
        return traducirAlIdiomaSeleccionado(dataList);
    }
    private ArrayList<String> traducirAlIdiomaSeleccionado(ArrayList<String> dataList) {
        ArrayList<String> translatedList = new ArrayList<>();
        for (String line : dataList) {
            translatedList.add(traducir(line));
        }
        return translatedList;
    }

    private String traducir(String texto) {
        String pregunta = "Traduce esto al idioma inglés, solo dame la respuesta, no agregue nada mas: " + texto;
        ConexionIA conexionIA = new ConexionIA();
        String respuesta = conexionIA.chatGPT(pregunta);
        return respuesta;
    }
}
