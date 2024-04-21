package com.proyectoDiseno.Authentication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

public class Autenticar {
    public static String generarLlaveSecreta() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public static String ObtenerCodigoGoogle(String llaveSecreta, String cuenta){
        String empresa = "Jimaye";
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(empresa + ":" + cuenta, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(llaveSecreta, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(empresa, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void crearQR(String codigoDatos) throws WriterException, IOException {
        // Ruta relativa dentro del proyecto donde se guardará la imagen del código QR
        String rutaRelativa = "src/main/resources/templates/codigo.png";

        // Obtener la ruta absoluta del proyecto
        String rutaProyecto = System.getProperty("user.dir");
        String rutaCompleta = rutaProyecto + File.separator + rutaRelativa;

        BitMatrix matrix = new MultiFormatWriter().encode(codigoDatos, BarcodeFormat.QR_CODE,
                500, 500);
        try (FileOutputStream out = new FileOutputStream(rutaCompleta)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }

    public static String obtenerCodigoMostrado(String llaveSecreta) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(llaveSecreta);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
}