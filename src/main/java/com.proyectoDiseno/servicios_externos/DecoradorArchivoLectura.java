package com.proyectoDiseno.servicios_externos;

import java.util.ArrayList;

public class DecoradorArchivoLectura implements IArchivoLectura{
    protected IArchivoLectura archivoLectura;
    public DecoradorArchivoLectura(IArchivoLectura archivoLectura){
        this.archivoLectura = archivoLectura;
    }
    @Override
    public ArrayList<String> leerTxtFiles(String[] fileNames) {
        return archivoLectura.leerTxtFiles(fileNames);
    }
}
