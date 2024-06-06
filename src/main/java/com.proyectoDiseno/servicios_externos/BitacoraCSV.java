package com.proyectoDiseno.servicios_externos;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BitacoraCSV extends Bitacora{
    String rutaProyecto = System.getProperty("user.dir");
    private final String nombreDelArchivo = rutaProyecto + "/src/main/resources/templates/bitacora.csv";
    private static int idBitacora = 0;
    @Override
    public void escribirBitacora(String accion, String ip, String sistemaOperativo, String pais, String fechaHora, String usuario) {
        File file = new File(nombreDelArchivo);
        boolean exists = file.exists();
        try (FileWriter writer = new FileWriter(file, true)) {
            //Si no existe el documento se crea
            if (!exists) {
                // Escribir encabezado si el archivo no existe
                writer.write("ID,FechaHora,IP,SistemaOperativo,Pais,Accion\n");
            }
            idBitacora++;
            writer.write(idBitacora + "," + fechaHora + "," + ip + "," + sistemaOperativo + "," + pais + "," + accion + "," + usuario +"\n");

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
            String[] campos;
            if (linea.startsWith("\"") && linea.endsWith("\"")) {
                // Si la línea comienza y termina con comillas, quitar las comillas y dividir por ","
                linea = linea.substring(1, linea.length() - 1); // Eliminar comillas iniciales y finales
                campos = linea.split("\",\"");
            } else {
                // Si la línea no tiene comillas al principio y al final, dividir directamente por ","
                campos = linea.split(",");
            }

            if (campos.length > 1) {
                try {
                    String fechaHoraString = campos[1];
                    Date fechaHoraRegistro = sdf.parse(fechaHoraString);

                    // Separar la fecha y la hora
                    String fechaRegistro = new SimpleDateFormat("yyyy-MM-dd").format(fechaHoraRegistro);
                    Date horaRegistro = sdfHora.parse(new SimpleDateFormat("HH:mm").format(fechaHoraRegistro));

                    // Obtener el usuario del registro
                    String usuarioRegistro = campos[6];

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
            if (linea.startsWith("ID,")) {
                registrosFiltrados.add(linea);
                continue;
            }

            // Dividir las líneas en campos por comas
            String[] campos;
            if (linea.startsWith("\"") && linea.endsWith("\"")) {
                // Si la línea comienza y termina con comillas, quitar las comillas y dividir por ","
                linea = linea.substring(1, linea.length() - 1); // Eliminar comillas iniciales y finales
                campos = linea.split("\",\"");
            } else {
                // Si la línea no tiene comillas al principio y al final, dividir directamente por ","
                campos = linea.split(",");
            }

            if (campos.length > 1) {
                try {
                    String fechaHoraString = campos[1];
                    String usuario = campos[6];
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
    public ArrayList<String> consultaDeTodosLosRegistrosPorUsuario(String usuarioSeleccionado) {
        ArrayList<String> registrosUsuario = new ArrayList<>();
        ArrayList<String> registros = consultarDeTodosLosRegistros();

        // Verificar si usuarioId es nulo antes de continuar
        if (usuarioSeleccionado == null) {
            System.err.println("El ID de usuario es nulo.");
            return registrosUsuario;
        }

        // Recorrer los registros y filtrar por el ID de usuario
        for (String linea : registros) {
            // Saltar la cabecera
            if (linea.startsWith("ID,")) {
                registrosUsuario.add(linea);
                continue;
            }

            // Dividir las líneas en campos por comas, evitando problemas con comillas
            String[] campos;
            if (linea.startsWith("\"") && linea.endsWith("\"")) {
                // Si la línea comienza y termina con comillas, quitar las comillas y dividir por ","
                linea = linea.substring(1, linea.length() - 1); // Eliminar comillas iniciales y finales
                campos = linea.split("\",\"");
            } else {
                // Si la línea no tiene comillas al principio y al final, dividir directamente por ","
                campos = linea.split(",");
            }

            if (campos.length > 1) {
                // Obtener el ID de usuario
                String usuario = campos[6];

                // Verificar si el ID de usuario coincide con el proporcionado
                if (usuarioSeleccionado.equals(usuario)) {
                    registrosUsuario.add(linea);
                }
            } else {
                System.err.println("Formato de línea inesperado: " + linea);
            }
        }

        return registrosUsuario;
    }

}

