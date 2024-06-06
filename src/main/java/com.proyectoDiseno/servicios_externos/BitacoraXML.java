package com.proyectoDiseno.servicios_externos;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BitacoraXML extends Bitacora {
    String rutaProyecto = System.getProperty("user.dir");
    private final String nombreDelArchivo = rutaProyecto + "/src/main/resources/templates/bitacora.xml";
    private static int idBitacora = 0;

    @Override
    public void escribirBitacora(String accion, String ip, String sistemaOperativo, String pais, String fechaHora, String usuario) {
        File file = new File(nombreDelArchivo);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            if (file.exists()) {
                doc = builder.parse(file);
            } else {
                doc = builder.newDocument();
                Element rootElement = doc.createElement("bitacoras");
                doc.appendChild(rootElement);
            }

            idBitacora++;
            Element registro = doc.createElement("registro");

            Element idElement = doc.createElement("ID");
            idElement.appendChild(doc.createTextNode(String.valueOf(idBitacora)));
            registro.appendChild(idElement);

            Element fechaHoraElement = doc.createElement("fechaHora");
            fechaHoraElement.appendChild(doc.createTextNode(fechaHora));
            registro.appendChild(fechaHoraElement);

            Element ipElement = doc.createElement("ip");
            ipElement.appendChild(doc.createTextNode(ip));
            registro.appendChild(ipElement);

            Element sistemaOperativoElement = doc.createElement("sistemaOperativo");
            sistemaOperativoElement.appendChild(doc.createTextNode(sistemaOperativo));
            registro.appendChild(sistemaOperativoElement);

            Element paisElement = doc.createElement("pais");
            paisElement.appendChild(doc.createTextNode(pais));
            registro.appendChild(paisElement);

            Element accionElement = doc.createElement("accion");
            accionElement.appendChild(doc.createTextNode(accion));
            registro.appendChild(accionElement);

            Element usuarioElement = doc.createElement("usuario");
            usuarioElement.appendChild(doc.createTextNode(usuario));
            registro.appendChild(usuarioElement);

            doc.getDocumentElement().appendChild(registro);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreDelArchivo));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> consultarDeTodosLosRegistros() {
        ArrayList<String> registrosXml = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(nombreDelArchivo));
            NodeList registrosNodeList = document.getElementsByTagName("registro");

            for (int i = 0; i < registrosNodeList.getLength(); i++) {
                Node registroNode = registrosNodeList.item(i);
                if (registroNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element registroElement = (Element) registroNode;

                    StringBuilder xmlBuilder = new StringBuilder();
                    xmlBuilder.append("<registro>\n");
                    xmlBuilder.append("  <ID>").append(registroElement.getElementsByTagName("ID").item(0).getTextContent()).append("</ID>\n");
                    xmlBuilder.append("  <fechaHora>").append(registroElement.getElementsByTagName("fechaHora").item(0).getTextContent()).append("</fechaHora>\n");
                    xmlBuilder.append("  <ip>").append(registroElement.getElementsByTagName("ip").item(0).getTextContent()).append("</ip>\n");
                    xmlBuilder.append("  <sistemaOperativo>").append(registroElement.getElementsByTagName("sistemaOperativo").item(0).getTextContent()).append("</sistemaOperativo>\n");
                    xmlBuilder.append("  <pais>").append(registroElement.getElementsByTagName("pais").item(0).getTextContent()).append("</pais>\n");
                    xmlBuilder.append("  <accion>").append(registroElement.getElementsByTagName("accion").item(0).getTextContent()).append("</accion>\n");
                    xmlBuilder.append("  <usuario>").append(registroElement.getElementsByTagName("usuario").item(0).getTextContent()).append("</usuario>\n");
                    xmlBuilder.append("</registro>");

                    registrosXml.add(xmlBuilder.toString());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return registrosXml;
    }

    @Override
    public ArrayList<String> consultaPorHoras(String horaInicio, String horaFin, String usuario) {
        ArrayList<String> registrosFiltrados = new ArrayList<>();
        ArrayList<String> registrosHoy = consultaDeHoyPorUsuario(usuario);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (String registro : registrosHoy) {
            try {
                String fechaHoraString = registro.substring(registro.indexOf("<fechaHora>") + 11, registro.indexOf("</fechaHora>")).trim();
                Date fechaHoraRegistro = sdf.parse(fechaHoraString);
                Date horaInicioDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + horaInicio);
                Date horaFinDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + horaFin);

                if (fechaHoraRegistro.after(horaInicioDate) && fechaHoraRegistro.before(horaFinDate)) {
                    registrosFiltrados.add(registro);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return registrosFiltrados;
    }

    @Override
    public ArrayList<String> consultaDeHoyPorUsuario(String idUsuario) {
        ArrayList<String> registrosFiltrados = new ArrayList<>();
        ArrayList<String> registrosUsuario = consultaDeTodosLosRegistrosPorUsuario(idUsuario);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        for (String registro : registrosUsuario) {
            try {
                String fechaHoraString = registro.substring(registro.indexOf("<fechaHora>") + 11, registro.indexOf("</fechaHora>")).trim();
                String fechaRegistro = fechaHoraString.split(" ")[0];

                if (today.equals(fechaRegistro)) {
                    registrosFiltrados.add(registro);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return registrosFiltrados;
    }

    @Override
    public ArrayList<String> consultaDeTodosLosRegistrosPorUsuario(String usuarioId) {
        ArrayList<String> registrosFiltrados = new ArrayList<>();

        if (usuarioId == null) {
            System.err.println("El ID de usuario es nulo");
            return registrosFiltrados;
        }

        try {
            List<String> registrosGenerales = consultarDeTodosLosRegistros();

            for (String registro : registrosGenerales) {
                try {
                    String usuario = registro.substring(registro.indexOf("<usuario>") + 9, registro.indexOf("</usuario>")).trim();

                    if (usuarioId.equals(usuario)) {
                        registrosFiltrados.add(registro);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return registrosFiltrados;
    }
}
