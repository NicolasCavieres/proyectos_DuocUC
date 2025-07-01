/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.io;

/**
 *
 * @author ariel
 */
import com.comiccollector.modelo.Comics;
import com.comiccollector.excepciones.ExtensionExcepciones;
import com.comiccollector.excepciones.TipoError;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCSV {
    private static final String RUTA = "data/comics.csv";

    public List<Comics> leerComics() throws ExtensionExcepciones {
        if (!RUTA.endsWith(".csv"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        List<Comics> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            br.readLine(); // cabecera
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    String[] f = linea.split(";");
                    Comics c = new Comics(
                        f[0], f[1],
                        f[2],
                        Integer.parseInt(f[3]),
                        Boolean.parseBoolean(f[4]),
                        Double.parseDouble(f[5])
                    );
                    lista.add(c);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new ExtensionExcepciones("Error de formato en línea de cómic: " + linea, TipoError.FORMATO_INVALIDO);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ExtensionExcepciones("Archivo no encontrado: " + RUTA, TipoError.ERROR_ARCHIVO);
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al leer el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
        return lista;
    }

    public void escribirComics(List<Comics> lista) throws ExtensionExcepciones {
        if (!RUTA.endsWith(".csv"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA))) {
            bw.write("titulo;autor;genero;anio;disponibilidad;precio");
            bw.newLine();
            for (Comics c : lista) {
                bw.write(String.format("%s;%s;%s;%d;%b;%.0f",
                    c.getTitulo(),
                    c.getAutor(),
                    c.getGenero(),
                    c.getAnio(),
                    c.estaDisponible(),
                    c.getPrecio()
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al escribir el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
    }

    /**
     * Actualiza la línea de un cómic en el archivo, manteniendo el resto igual.
     * Si el cómic no existe, no hace nada.
     */
    public void actualizarLineaComic(Comics comicActualizado) throws ExtensionExcepciones {
        if (!RUTA.endsWith(".csv"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        File archivo = new File(RUTA);
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean esCabecera = true;
            while ((linea = br.readLine()) != null) {
                if (esCabecera) {
                    lineas.add(linea);
                    esCabecera = false;
                    continue;
                }
                String[] partes = linea.split(";");
                if (!partes[0].equalsIgnoreCase(comicActualizado.getTitulo())) {
                    lineas.add(linea);
                } else {
                    // Reemplazar por la nueva línea del cómic actualizado
                    lineas.add(String.format("%s;%s;%s;%d;%b;%.0f",
                        comicActualizado.getTitulo(),
                        comicActualizado.getAutor(),
                        comicActualizado.getGenero(),
                        comicActualizado.getAnio(),
                        comicActualizado.estaDisponible(),
                        comicActualizado.getPrecio()
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            throw new ExtensionExcepciones("Archivo no encontrado: " + RUTA, TipoError.ERROR_ARCHIVO);
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al leer el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String l : lineas) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al escribir el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
    }
}