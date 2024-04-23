
package com.proyectoDiseno.servicios_externos;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AudioGoogle {
    private static final String TTS_URL = "https://api.openai.com/v1/audio/speech";
    private static final String OPENAI_API_KEY = "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";

    public static String crearAudio(String texto) {
        String rutaProyecto = System.getProperty("user.dir");
        String rutaArchivo = rutaProyecto + "/src/main/resources/templates/";

        try {
            JsonObject jsonBody = Json.createObjectBuilder()
                    .add("voice", "alloy")
                    .add("input", texto)
                    .add("model", "tts-1")
                    .build();
            StringWriter stringWriter = new StringWriter();
            try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
                jsonWriter.writeObject(jsonBody);
            }
            String postBody = stringWriter.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TTS_URL))
                    .header("Authorization", "Bearer " + OPENAI_API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            Path rutaCompleta = Paths.get(rutaArchivo + "audio_generado.mp3");

            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(rutaCompleta));

            Path filePath = response.body();
            return filePath.toString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("No se pudo generar el audio", e);
        }
    }

}
