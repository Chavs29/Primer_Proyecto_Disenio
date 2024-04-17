package com.proyectoDiseno.ConexionIA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConexionIA {
    public static String chatGPT(String pregunta) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model =  "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\",\"content\": \"" + pregunta + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new
                    OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String respuesta) {
        int start = respuesta.indexOf("content")+ 11;
        int end = respuesta.indexOf("\"", start);
        return respuesta.substring(start, end);
    }
}