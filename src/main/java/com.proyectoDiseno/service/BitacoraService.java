package com.proyectoDiseno.service;


import com.proyectoDiseno.servicios_externos.BitacoraCSV;
import com.proyectoDiseno.servicios_externos.BitacoraTramaPlana;
import com.proyectoDiseno.servicios_externos.BitacoraXML;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BitacoraService {

    private final BitacoraCSV bitacoraCSV = new BitacoraCSV();
    private final BitacoraTramaPlana bitacoraTramaPlana = new BitacoraTramaPlana();
    private final BitacoraXML bitacoraXML = new BitacoraXML();

    public void escribirBitacoras(String accion, String ip, String sistemaOperativo, String pais, String fechaHora, String usuario) {
        bitacoraCSV.escribirBitacora(accion, ip, sistemaOperativo, pais, fechaHora,usuario);
        bitacoraTramaPlana.escribirBitacora(accion, ip, sistemaOperativo, pais, fechaHora,usuario);
        bitacoraXML.escribirBitacora(accion, ip, sistemaOperativo, pais, fechaHora,usuario);
    }

    public ArrayList<String> consultarDeTodosLosRegistros(String tipo){
        ArrayList<String> registros = new ArrayList<String>();

        switch (tipo.toLowerCase()) {
            case "csv":
                registros = bitacoraCSV.consultarDeTodosLosRegistros();
                break;
            case "xml":
                registros = bitacoraXML.consultarDeTodosLosRegistros();
                break;
            case "trama plana":
                registros = bitacoraTramaPlana.consultarDeTodosLosRegistros();
                break;
            default:
                System.out.println("Tipo no reconocido: " + tipo);
                break;
        }

        return registros;
    }

    public ArrayList<String> consultaPorHoras(String tipo, String horaInicio, String horaFin, String usuario){
        ArrayList<String> registros = new ArrayList<String>();

        switch (tipo.toLowerCase()) {
            case "csv":
                registros = bitacoraCSV.consultaPorHoras(horaInicio,horaFin,usuario);
                break;
            case "xml":
                registros = bitacoraXML.consultaPorHoras(horaInicio,horaFin,usuario);
                break;
            case "trama plana":
                registros = bitacoraTramaPlana.consultaPorHoras(horaInicio,horaFin,usuario);
                break;
            default:
                System.out.println("Tipo no reconocido: " + tipo);
                break;
        }

        return registros;
    }

    public ArrayList<String> consultaDeHoyPorUsuario(String tipo, String usuario){
        ArrayList<String> registros = new ArrayList<String>();

        switch (tipo.toLowerCase()) {
            case "csv":
                registros = bitacoraCSV.consultaDeHoyPorUsuario(usuario);
                break;
            case "xml":
                registros = bitacoraXML.consultaDeHoyPorUsuario(usuario);
                break;
            case "trama plana":
                registros = bitacoraTramaPlana.consultaDeHoyPorUsuario(usuario);
                break;
            default:
                System.out.println("Tipo no reconocido: " + tipo);
                break;
        }

        return registros;
    }

    public ArrayList<String> consultaDeTodosLosRegistrosPorUsuario(String tipo, String usuario){
        ArrayList<String> registros = new ArrayList<String>();

        switch (tipo.toLowerCase()) {
            case "csv":
                registros = bitacoraCSV.consultaDeTodosLosRegistrosPorUsuario(usuario);
                break;
            case "xml":
                registros = bitacoraXML.consultaDeTodosLosRegistrosPorUsuario(usuario);
                break;
            case "trama plana":
                registros = bitacoraTramaPlana.consultaDeTodosLosRegistrosPorUsuario(usuario);
                break;
            default:
                System.out.println("Tipo no reconocido: " + tipo);
                break;
        }

        return registros;
    }
}
