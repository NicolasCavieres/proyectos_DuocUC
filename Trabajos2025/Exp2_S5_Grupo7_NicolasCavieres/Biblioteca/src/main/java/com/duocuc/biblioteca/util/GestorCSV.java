/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.util;

import com.duocuc.biblioteca.excepciones.ExtensionExcepciones;
import com.duocuc.biblioteca.excepciones.TipoError;
import com.duocuc.biblioteca.modelo.Usuario;
import com.duocuc.biblioteca.modelo.Libro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GestorCSV {
    private static final String SEPARADOR = ";";

    public static void verificarArchivo(String archivo) throws ExtensionExcepciones {
        try {
            File file = new File(archivo);
            file.createNewFile(); // Si el archivo ya existe, no hace nada
        } catch (IOException e) {
            throw new ExtensionExcepciones(TipoError.ERROR_ARCHIVO, "Error al crear archivo: " + archivo, e);
        }
    }

    public static void inicializarArchivos() throws ExtensionExcepciones {
        verificarArchivo("usuarios.csv");
        verificarArchivo("libros.csv");
    }

    public static HashMap<String, Usuario> cargarDatosUsuarios() throws ExtensionExcepciones {
        return cargarUsuarios("usuarios.csv");
    }

    public static ArrayList<Libro> cargarDatosLibros() throws ExtensionExcepciones {
        return cargarLibros("libros.csv");
    }

    public static void guardarDatos(HashMap<String, Usuario> usuarios, ArrayList<Libro> libros) throws ExtensionExcepciones {
        guardarUsuarios("usuarios.csv", usuarios);
        guardarLibros("libros.csv", libros);
    }

    public static HashMap<String, Usuario> cargarUsuarios(String archivo) throws ExtensionExcepciones {
        HashMap<String, Usuario> usuarios = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(SEPARADOR);
                if (partes.length == 5) {
                    try {
                        Usuario u = new Usuario(partes[0], partes[1], partes[2], partes[3], partes[4]);
                        usuarios.put(u.getRut(), u);
                    } catch (ExtensionExcepciones ex) {
                        System.err.println("Usuario inválido en CSV: " + ex.getMessage());
                    }
                } else if (!linea.trim().isEmpty()) {
                    System.err.println("Línea inválida en usuarios.csv: " + linea);
                }
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones(TipoError.ERROR_ARCHIVO, "Error al leer usuarios: " + e.getMessage(), e);
        }
        return usuarios;
    }

    public static void guardarUsuarios(String archivo, HashMap<String, Usuario> usuarios) throws ExtensionExcepciones {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Usuario u : usuarios.values()) {
                writer.write(u.getNombre() + SEPARADOR + u.getApellido() + SEPARADOR + u.getRut() + SEPARADOR +
                        u.getTelefono() + SEPARADOR + u.getCorreo() + "\n");
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones(TipoError.ERROR_ARCHIVO, "Error al guardar usuarios: " + e.getMessage(), e);
        }
    }

    public static ArrayList<Libro> cargarLibros(String archivo) throws ExtensionExcepciones {
        ArrayList<Libro> libros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(SEPARADOR);
                if (partes.length == 5) {
                    try {
                        libros.add(new Libro(
                                partes[0],
                                partes[1],
                                Integer.parseInt(partes[2]),
                                Boolean.parseBoolean(partes[3]),
                                partes[4]
                        ));
                    } catch (NumberFormatException e) {
                        System.err.println("Año inválido en libro: " + linea);
                    }
                } else if (!linea.trim().isEmpty()) {
                    System.err.println("Línea inválida en libros.csv: " + linea);
                }
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones(TipoError.ERROR_ARCHIVO, "Error al leer libros: " + e.getMessage(), e);
        }
        return libros;
    }

    public static void guardarLibros(String archivo, ArrayList<Libro> libros) throws ExtensionExcepciones {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Libro l : libros) {
                writer.write(l.getNombre() + SEPARADOR + l.getAutor() + SEPARADOR +
                        l.getAnio() + SEPARADOR + l.isDisponible() + SEPARADOR +
                        l.getUbicacion() + "\n");
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones(TipoError.ERROR_ARCHIVO, "Error al guardar libros: " + e.getMessage(), e);
        }
    }
}
