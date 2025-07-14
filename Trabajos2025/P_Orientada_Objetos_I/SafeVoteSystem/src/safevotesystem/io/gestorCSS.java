/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package safevotesystem.io;

/**
 *
 * @author ariel
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gestorCSS {

    /**
     * Lee una lista de enteros desde un archivo CSV (una columna o separados por coma).
     *
     * @param rutaArchivo Ruta al archivo CSV.
     * @return Lista de números enteros leídos.
     * @throws IOException Si hay error al leer el archivo.
     */
    public List<Integer> leerDesdeArchivo(String nombreArchivoSinExtension) throws IOException {
        String rutaCompleta = "src/archivos/" + nombreArchivoSinExtension + ".csv";
        List<Integer> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("[,;\\s]+");
                for (String parte : partes) {
                    try {
                        numeros.add(Integer.parseInt(parte.trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Valor inválido: " + parte);
                    }
                }
            }
        }
        return numeros;
    }
}