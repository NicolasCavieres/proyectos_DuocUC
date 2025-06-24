/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

import com.duocuc.biblioteca.excepciones.ExtensionExcepciones;
import com.duocuc.biblioteca.excepciones.TipoError;

/**
 *
 * @author ariel
 */

public class Libro {
    private String nombre;
    private String autor;
    private int anio;
    private boolean disponible;
    private String ubicacion;

    public Libro(String nombre, String autor, int anio, boolean disponible, String ubicacion) {
        this.nombre = nombre;
        this.autor = autor;
        this.anio = anio;
        this.disponible = disponible;
        this.ubicacion = ubicacion;
    }

    // Métodos

    public static Libro registrarLibro(Scanner scanner, ArrayList<Libro> libros) throws ExtensionExcepciones {
        System.out.println("\n=== REGISTRO DE LIBRO ===");
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                throw new ExtensionExcepciones(TipoError.DATO_INVALIDO, "El nombre del libro no puede estar vacío.");
            }
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            if (autor.trim().isEmpty()) {
                throw new ExtensionExcepciones(TipoError.DATO_INVALIDO, "El autor no puede estar vacío.");
            }
            System.out.print("Año: ");
            String anioStr = scanner.nextLine();
            int anio;
            try {
                anio = Integer.parseInt(anioStr);
            } catch (NumberFormatException e) {
                throw new ExtensionExcepciones(TipoError.FORMATO_INVALIDO, "El año debe ser un número válido.");
            }

            for (Libro l : libros) {
                if (l.getNombre().equalsIgnoreCase(nombre) && l.getAutor().equalsIgnoreCase(autor)) {
                    throw new ExtensionExcepciones(TipoError.DATO_DUPLICADO, "Ya existe un libro con ese nombre y autor.");
                }
            }

            Libro nuevoLibro = new Libro(nombre, autor, anio, true, "Biblioteca");
            libros.add(nuevoLibro);
            com.duocuc.biblioteca.visor.InfoLibro.mostrarInformacionLibro(nuevoLibro);
            System.out.println("✅ Libro registrado correctamente.");
            return nuevoLibro;
        } catch (ExtensionExcepciones e) {
            System.err.println(e.toString());
            throw e;
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.ERROR_SISTEMA, "Error inesperado al registrar libro.", e);
        }
    }

    public static Libro prestarLibro(Scanner scanner, HashMap<String, Usuario> usuarios, ArrayList<Libro> libros) {
        try {
            System.out.println("\n=== PRÉSTAMO DE LIBRO ===");
            String rut = Usuario.leerRut(scanner);
            System.out.print("Nombre del libro: ");
            String nombreLibro = scanner.nextLine();

            com.duocuc.biblioteca.servicio.Prestamo.registrarPrestamo(usuarios, libros, rut, nombreLibro);
            // Buscar el libro prestado y retornarlo
            for (Libro l : libros) {
                if (l.getNombre().equalsIgnoreCase(nombreLibro)) {
                    return l;
                }
            }
        } catch (com.duocuc.biblioteca.excepciones.ExtensionExcepciones e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println("Error inesperado en préstamo: " + e.getMessage());
        }
        return null;
    }

    public static Libro devolverLibro(Scanner scanner, HashMap<String, Usuario> usuarios, ArrayList<Libro> libros) {
        try {
            System.out.println("\n=== DEVOLUCIÓN DE LIBRO ===");
            String rut = Usuario.leerRut(scanner);
            System.out.print("Nombre del libro: ");
            String nombreLibro = scanner.nextLine();

            com.duocuc.biblioteca.servicio.Prestamo.devolverPrestamo(usuarios, libros, rut, nombreLibro);
            // Buscar el libro devuelto y retornarlo
            for (Libro l : libros) {
                if (l.getNombre().equalsIgnoreCase(nombreLibro)) {
                    return l;
                }
            }
        } catch (com.duocuc.biblioteca.excepciones.ExtensionExcepciones e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println("Error inesperado en devolución: " + e.getMessage());
        }
        return null;
    }

    public static void mostrarLibrosDisponibles(TreeSet<Libro> librosDisponibles) {
        System.out.println("\n=== LIBROS DISPONIBLES ===\n");
        if (librosDisponibles.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        } else {
            for (Libro libro : librosDisponibles) {
                com.duocuc.biblioteca.visor.InfoLibro.mostrarInformacionLibro(libro);
            }
        }
    }

    public static void mostrarLibrosPrestados(HashMap<String, Usuario> usuarios) {
        System.out.println("\n=== LIBROS PRESTADOS ===\n");
        boolean hayPrestados = false;
        for (Usuario usuario : usuarios.values()) {
            if (!usuario.getLibrosPrestados().isEmpty()) {
                System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido() + " (" + usuario.getRut() + ")");
                for (Libro libro : usuario.getLibrosPrestados()) {
                    com.duocuc.biblioteca.visor.InfoLibro.mostrarInformacionLibro(libro);
                }
                hayPrestados = true;
            }
        }
        if (!hayPrestados) {
            System.out.println("No hay libros prestados actualmente.");
        }
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    // Métodos auxiliares
    
    public String obtenerInfo() {
        return nombre + " - " + autor + " (" + anio + ")";
    }
}