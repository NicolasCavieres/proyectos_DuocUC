/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.servicio;

/**
 *
 * @author ariel
 */

import com.duocuc.biblioteca.modelo.Libro;
import com.duocuc.biblioteca.modelo.Usuario;
import com.duocuc.biblioteca.excepciones.ExtensionExcepciones;
import com.duocuc.biblioteca.excepciones.TipoError;

import java.util.ArrayList;
import java.util.HashMap;

public class Prestamo {

    public static void registrarPrestamo(HashMap<String, Usuario> usuarios,
                                         ArrayList<Libro> libros,
                                         String rutUsuario,
                                         String nombreLibro) throws ExtensionExcepciones {
        try {
            if (rutUsuario == null || rutUsuario.trim().isEmpty()) {
                throw new Exception("El RUT no puede estar vacío");
            }
            Usuario usuario = usuarios.get(rutUsuario);
            if (usuario == null) {
                throw new Exception("Usuario no registrado con RUT: " + rutUsuario);
            }

            Libro libro = null;
            for (Libro l : libros) {
                if (l.getNombre().equalsIgnoreCase(nombreLibro)) {
                    libro = l;
                    break;
                }
            }
            if (libro == null) {
                throw new Exception("El libro '" + nombreLibro + "' no existe.");
            }
            if (!libro.isDisponible()) {
                throw new Exception("El libro no está disponible.");
            }

            libro.setDisponible(false);
            usuario.agregarLibroPrestado(libro);
            System.out.println("Préstamo registrado con éxito.");
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.DATO_INVALIDO, e.getMessage(), e);
        }
    }

    public static void devolverPrestamo(HashMap<String, Usuario> usuarios,
                                        ArrayList<Libro> libros,
                                        String rutUsuario,
                                        String nombreLibro) throws ExtensionExcepciones {
        try {
            Usuario usuario = usuarios.get(rutUsuario);
            if (usuario == null) {
                throw new Exception("Usuario no registrado con RUT: " + rutUsuario);
            }

            Libro libroADevolver = null;
            for (Libro libro : usuario.getLibrosPrestados()) {
                if (libro.getNombre().equalsIgnoreCase(nombreLibro)) {
                    libroADevolver = libro;
                    break;
                }
            }
            if (libroADevolver == null) {
                throw new Exception("El usuario no tiene prestado el libro '" + nombreLibro + "'.");
            }

            libroADevolver.setDisponible(true);
            usuario.devolverLibro(libroADevolver);
            System.out.println("Devolución registrada con éxito.");
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.DATO_INVALIDO, e.getMessage(), e);
        }
    }
}