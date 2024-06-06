package com.proyectoDiseno.servicios_externos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public abstract class Bitacora {

    private static int idBitacora = 0;

    public Bitacora() {
        idBitacora++;
    }

    public int getIdBitacora() {
        return idBitacora;
    }






    public abstract void escribirBitacora(String accion, String ip, String sistemaOperativo, String pais, String fechaHora, String Usuario);
    public abstract ArrayList<String> consultarDeTodosLosRegistros();
    public abstract ArrayList<String> consultaPorHoras(String horaInicio, String horaFin, String usuario);
    public abstract ArrayList<String> consultaDeHoyPorUsuario(String usuario);
    public abstract ArrayList<String> consultaDeTodosLosRegistrosPorUsuario(String usuario);
}