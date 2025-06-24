/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.duocuc.biblioteca;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

import com.duocuc.biblioteca.excepciones.ExtensionExcepciones;
import com.duocuc.biblioteca.modelo.Libro;
import com.duocuc.biblioteca.modelo.Usuario;
import com.duocuc.biblioteca.util.GestorCSV;
import com.duocuc.biblioteca.visor.Menu;

public class Biblioteca {
    public static void main(String[] args) {
        // Configuración global de UTF-8
        System.setProperty("file.encoding", "UTF-8");
        try {
            // Forzar reinicialización del charset
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);

            // Configurar salida estándar
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            System.err.println("Error configurando encoding: " + e.getMessage());
        }

        HashMap<String, Usuario> usuarios = new HashMap<>();
        ArrayList<Libro> libros = new ArrayList<>();
        TreeSet<Libro> librosDisponibles = new TreeSet<>(Comparator.comparing(Libro::getNombre, String.CASE_INSENSITIVE_ORDER));
        Scanner scanner = new Scanner(System.in);

        try {
            GestorCSV.inicializarArchivos();
            usuarios = GestorCSV.cargarDatosUsuarios();
            libros = GestorCSV.cargarDatosLibros();
            // Llenar el TreeSet con los libros disponibles
            for (Libro libro : libros) {
                if (libro.isDisponible()) {
                    librosDisponibles.add(libro);
                }
            }
        } catch (ExtensionExcepciones e) {
            System.err.println("Error al inicializar/cargar archivos: " + e.toString());
        }

        ejecutarMenu(scanner, usuarios, libros, librosDisponibles);
    }

    public static void ejecutarMenu(Scanner scanner, HashMap<String, Usuario> usuarios, ArrayList<Libro> libros, TreeSet<Libro> librosDisponibles) {
        boolean continuar = true;
        while (continuar) {
            try {
                int opcion = Menu.mostrarMenu(scanner);
                switch (opcion) {
                    case 1 -> Usuario.registrarUsuario(scanner, usuarios);
                    case 2 -> {
                        Libro nuevoLibro = Libro.registrarLibro(scanner, libros);
                        if (nuevoLibro != null && nuevoLibro.isDisponible()) {
                            librosDisponibles.add(nuevoLibro);
                        }
                    }
                    case 3 -> Libro.mostrarLibrosDisponibles(librosDisponibles);
                    case 4 -> Libro.mostrarLibrosPrestados(usuarios);
                    case 5 -> {
                        Libro libroPrestado = Libro.prestarLibro(scanner, usuarios, libros);
                        if (libroPrestado != null) {
                            librosDisponibles.remove(libroPrestado);
                        }
                    }
                    case 6 -> {
                        Libro libroDevuelto = Libro.devolverLibro(scanner, usuarios, libros);
                        if (libroDevuelto != null && libroDevuelto.isDisponible()) {
                            librosDisponibles.add(libroDevuelto);
                        }
                    }
                    case 7 -> {
                        continuar = false;
                        GestorCSV.guardarDatos(usuarios, libros);
                        System.out.println("¡Gracias por usar la biblioteca!");
                    }
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();  // Para depuración
            }
        }
        scanner.close();
    }
}