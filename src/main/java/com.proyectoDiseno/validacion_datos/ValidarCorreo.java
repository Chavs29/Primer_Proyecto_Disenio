package com.proyectoDiseno.validacion_datos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class ValidarCorreo {
    public ValidarCorreo(){
    }
    public static boolean verificacionDeEmail(String email) {
        String apiKey = "live_32d7a87ce2b333cb2aa7";
        String apiUrl = "https://api.emailable.com/v1/verify?email=" + email + "&api_key=" + apiKey;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(apiUrl);

            HttpResponse response = client.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString().contains("\"state\":\"deliverable\"");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
