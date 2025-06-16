/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.duocuc.biblioteca;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        try {
            GestorCSV.inicializarArchivos();
            usuarios = GestorCSV.cargarDatosUsuarios();
            libros = GestorCSV.cargarDatosLibros();
        } catch (ExtensionExcepciones e) {
            System.err.println("Error al inicializar/cargar archivos: " + e.toString());
        }

        ejecutarMenu(scanner, usuarios, libros);
    }

    public static void ejecutarMenu(Scanner scanner, HashMap<String, Usuario> usuarios, ArrayList<Libro> libros) {
        boolean continuar = true;
        while (continuar) {
            try {
                int opcion = Menu.mostrarMenu(scanner);
                switch (opcion) {
                    case 1 -> Usuario.registrarUsuario(scanner, usuarios);
                    case 2 -> Libro.registrarLibro(scanner, libros);
                    case 3 -> Libro.buscarLibro(scanner, libros);
                    case 4 -> Libro.prestarLibro(scanner, usuarios, libros);
                    case 5 -> Libro.devolverLibro(scanner, usuarios, libros);
                    case 6 -> {
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