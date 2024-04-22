package com.proyectoDiseno.WordCloud;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;


public class CrearWordCloud {

    public static void crearWordCloud(String texto)throws IOException{
        crearArchivo(texto);
        BufferedImage imagen =crearImagen();
        guardarImagen(imagen);
    }
    
    public static void crearArchivo(String texto) throws IOException{
        FileWriter archivoTexto = new FileWriter("texto.txt");
        archivoTexto.write(texto);
        archivoTexto.close();
        
    }
    public static BufferedImage crearImagen() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("texto.txt");
        final Dimension dimension = new Dimension(500, 500);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(200));
        wordCloud.setColorPalette(new ColorPalette(Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.GREEN, Color.WHITE));
        wordCloud.setFontScalar(new SqrtFontScalar(20, 40));
        wordCloud.build(wordFrequencies);
        BufferedImage image = wordCloud.getBufferedImage();
        return image;
    }
    
    public static void guardarImagen(BufferedImage imagen)throws IOException{
        String rutaProyecto = System.getProperty("user.dir");

        // Ruta relativa donde se guardará el archivo dentro del proyecto
        String rutaArchivo = rutaProyecto + "/src/main/resources/templates/imagenWordCloud.png";
        File outputFile = new File(rutaArchivo);
        ImageIO.write(imagen, "png", outputFile);
    }

}
