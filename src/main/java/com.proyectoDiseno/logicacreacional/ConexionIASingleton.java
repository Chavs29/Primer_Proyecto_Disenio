package com.proyectoDiseno.logicacreacional;

import com.proyectoDiseno.servicios_externos.ConexionIA;

public class ConexionIASingleton {
    private static ConexionIA  conexionIA = new ConexionIA();

    private ConexionIASingleton(){
    }
    public static ConexionIA getInstance(){
        return  conexionIA;
    }
}
