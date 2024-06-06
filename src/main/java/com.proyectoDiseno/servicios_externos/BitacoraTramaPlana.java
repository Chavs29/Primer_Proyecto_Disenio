package com.proyectoDiseno.servicios_externos;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BitacoraTramaPlana extends Bitacora{
    String rutaProyecto = System.getProperty("user.dir");
    private final String nombreDelArchivo = rutaProyecto + "/src/main/resources/templates/bitacora.txt";
    private static int idBitacora = 0;
    @Override
    public void escribirBitacora(String accion, String ip, String sistemaOperativo, String pais, String fechaHora, String usuario) {
        File file = new File(nombreDelArchivo);
        boolean exists = file.exists();

        try (FileWriter writer = new FileWriter(file, true)) {
            //Si no existe el documento se crea
            if (!exists) {
                writer.write("ID | Fecha y hora | IP | Sistema Operativo | Pais | Acción | Usuario \n");
            }
            idBitacora++;
            writer.write(idBitacora + " | " + fechaHora + " | " + ip + " | " + sistemaOperativo + " | " + pais + " | " + accion + " | " + usuario+"\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> consultarDeTodosLosRegistros() {
        ArrayList<String> registros = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreDelArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Simplemente agregar la línea completa al ArrayList
                registros.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return registros;
    }

    @Override
    public ArrayList<String> consultaPorHoras(String horaInicio, String horaFin, String idUsuario) {
        ArrayList<String> registrosFiltrados = new ArrayList<>();
        ArrayList<String> registros = consultarDeTodosLosRegistros();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Convertir horas de inicio y fin a Date
        Date horaInicioDate;
        Date horaFinDate;
        try {
            horaInicioDate = sdfHora.parse(horaInicio);
            horaFinDate = sdfHora.parse(horaFin);
        } catch (ParseException e) {
            e.printStackTrace();
            return registrosFiltrados; // Retornar vacío en caso de error
        }

        for (String linea : registros) {
            // Saltar la cabecera
            if (linea.startsWith("ID,")) {
                registrosFiltrados.add(linea);
                continue;
            }

            // Dividir las líneas en campos por comas
            String[] campos = linea.split(" \\| ");
            if (campos.length > 1) {
                try {
                    String fechaHoraString = campos[1].trim();
                    Date fechaHoraRegistro = sdf.parse(fechaHoraString);

                    // Separar la fecha y la hora
                    String fechaRegistro = new SimpleDateFormat("yyyy-MM-dd").format(fechaHoraRegistro);
                    Date horaRegistro = sdfHora.parse(new SimpleDateFormat("HH:mm").format(fechaHoraRegistro));

                    // Obtener el usuario del registro
                    String usuarioRegistro = campos[6].trim();

                    // Comparar las fechas, las horas y el usuario
                    if (today.equals(fechaRegistro) && !horaRegistro.before(horaInicioDate) && !horaRegistro.after(horaFinDate)
                            && idUsuario.equals(usuarioRegistro)) {
                        registrosFiltrados.add(linea);
                    }
                } catch (ParseException e) {
                    System.err.println("Error al parsear la fecha: " + e.getMessage());
                }
            } else {
                System.err.println("Formato de línea inesperado: " + linea);
            }
        }

        return registrosFiltrados;
    }


    @Override
    public ArrayList<String> consultaDeHoyPorUsuario(String idUsuario) {
        ArrayList<String> registrosFiltrados = new ArrayList<>();
        ArrayList<String> registros = consultarDeTodosLosRegistros();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        for (String linea : registros) {
            // Saltar la cabecera
            if (linea.startsWith("ID |")) {
                registrosFiltrados.add(linea);
                continue;
            }

            // Dividir las líneas en campos por " | "
            String[] campos = linea.split(" \\| ");
            if (campos.length > 1) {
                try {
                    String fechaHoraString = campos[1].trim();
                    String usuario = campos[6].trim();
                    Date fechaHoraRegistro = sdf.parse(fechaHoraString);

                    // Separar la fecha
                    String fechaRegistro = new SimpleDateFormat("yyyy-MM-dd").format(fechaHoraRegistro);

                    // Comparar la fecha y el ID de usuario
                    if (today.equals(fechaRegistro) && idUsuario.equals(usuario)) {
                        registrosFiltrados.add(linea);
                    }
                } catch (ParseException e) {
                    System.err.println("Error al parsear la fecha: " + e.getMessage());
                }
            } else {
                System.err.println("Formato de línea inesperado: " + linea);
            }
        }

        return registrosFiltrados;
    }

    @Override
    public ArrayList<String> consultaDeTodosLosRegistrosPorUsuario(String usuarioId) {
        ArrayList<String> registrosFiltrados = new ArrayList<>();
        ArrayList<String> registros = consultarDeTodosLosRegistros();

        for (String linea : registros) {
            // Saltar la cabecera
            if (linea.startsWith("ID |")) {
                registrosFiltrados.add(linea);
                continue;
            }

            // Dividir las líneas en campos por la barra vertical
            String[] campos = linea.split(" \\| ");
            if (campos.length > 1) {
                String usuario = campos[6].trim();

                // Comparar el ID de usuario
                if (usuarioId.equals(usuario)) {
                    registrosFiltrados.add(linea);
                }
            } else {
                System.err.println("Formato de línea inesperado: " + linea);
            }
        }

        return registrosFiltrados;
    }
}

